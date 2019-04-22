package com.gikee.usdtcollect.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gikee.usdtcollect.dao.USDTTransactionDao;
import com.gikee.usdtcollect.model.MessageReminder;
import com.gikee.usdtcollect.model.USDTMessage;
import com.gikee.usdtcollect.model.USDTTransaction;
import com.gikee.usdtcollect.service.USDTTransactionService;
import com.gikee.usdtcollect.utils.DateTransform;
import foundation.omni.rpc.OmniCLIClient;
import groovy.lang.Tuple6;
import lombok.extern.slf4j.Slf4j;
import org.bitcoinj.core.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class USDTTransactionServiceImpl implements USDTTransactionService {

    private static final String SIMPLESEND = "Simple Send";
    private static final String SENDALL = "Send All";
    private static final String SUBSENDS = "subsends";
    private static final String PROPERTYID = "propertyid";
    private static final String TYPE = "type";
    private static final String TETHERUS = "31";
    private static final String AMOUNT = "amount";
    private static final String DIVISIBLE = "divisible";

    @Autowired
    private OmniCLIClient omniCLIClient;

    @Autowired
    private DateTransform dateTransform;

    @Autowired
    private USDTTransactionDao usdtTransactionDao;

    @Override
    public int insertCollection(List<USDTTransaction> USDTTransactions) {
        return usdtTransactionDao.insertCollection(USDTTransactions);
    }

    @Override
    public Tuple6<List<USDTTransaction>, List<MessageReminder>, String, String, String, String> getUSDTTransaction(Integer blockNumber) {
        // 交易条数
        int transactionCount = 0;
        // 正确交易条数
        int validCount = 0;
        // 错误交易条数
        int inValidCount = 0;
        // 正确交易数值总数
        BigDecimal transactionValue = BigDecimal.ZERO;
        // 交易集合
        List<USDTTransaction> usdtTransactions = new ArrayList<>();
        // 大额交易集合
        List<MessageReminder> messageReminders = new ArrayList<>();
        try {
            log.info("开始循环获取当前交易信息,当前块高度为 : ---------------> {} <---------------", blockNumber);
            List<Sha256Hash> sha256Hashes = omniCLIClient.omniListBlockTransactions(blockNumber);
            if (!sha256Hashes.isEmpty()) {
                for (Sha256Hash txid : sha256Hashes) {
                    Map<String, Object> transaction = omniCLIClient.omniGetTransaction(txid);
                    USDTTransaction tx = getMapToInstance(transaction);
                    if (tx != null) {
                        usdtTransactions.add(tx);
                        if ("true".equals(tx.getValid())) {
                            validCount = validCount + 1;
                            BigDecimal value = new BigDecimal(tx.getValueNumber());
                            transactionValue = transactionValue.add(value);
                            if (value.compareTo(new BigDecimal("2000000")) >= 0) {
                                messageReminders.add(new MessageReminder(
                                        new USDTMessage(tx.getBlockNumber(), tx.getTransactionHash(), tx.getSendingAddress(),
                                                tx.getReferenceAddress(), tx.getValueNumber(), tx.getBlockTimeStamp())));
                            }
                        } else {
                            inValidCount = inValidCount + 1;
                        }
                    }
                }
                transactionCount = usdtTransactions.size();
            }
            log.info("循环获取当前交易信息结束,当前块高度为 : ---------------> {} <---------------", blockNumber);
        } catch (IOException e) {
            log.error("循环获取当前交易信息失败,当前块高度为 : ---------------> {} <---------------" + blockNumber);
            try {
                log.warn("循环获取当前交易信息失败,可能因为访问节点操作太频繁,开始休息 5 秒 : ---------------> {} <---------------" + blockNumber);
                Thread.sleep(5000);
                return getUSDTTransaction(blockNumber);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
        return new Tuple6(usdtTransactions, messageReminders, String.valueOf(transactionCount), transactionValue.toPlainString(), String.valueOf(validCount), String.valueOf(inValidCount));
    }

    public USDTTransaction getMapToInstance(Map<String, Object> transaction) {
        USDTTransaction usdtTransaction = null;
        if (!transaction.isEmpty()) {
            // map 转换为 json
            JSONObject info = JSON.parseObject(JSON.toJSONString(transaction));
            // 获取 transaction type
            String type = transaction.get(TYPE).toString();
            // 判断交易 type 是简单发送还是全部发送
            if (SENDALL.equals(type)) {
                JSONArray array = info.getJSONArray(SUBSENDS);
                if (array != null && array.size() > 0) {
                    for (Object obj : array) {
                        JSONObject jsonObject = JSON.parseObject(obj.toString());
                        String propertyId = jsonObject.getString(PROPERTYID);
                        if (TETHERUS.equals(propertyId)) {
                            info.put(PROPERTYID, TETHERUS);
                            info.put(DIVISIBLE, jsonObject.getString(DIVISIBLE));
                            info.put(AMOUNT, jsonObject.getString(AMOUNT));
                            info.remove(SUBSENDS);
                            usdtTransaction = JSON.parseObject(JSON.toJSONString(info), USDTTransaction.class);
                            break;
                        }
                    }
                }
            } else if (SIMPLESEND.equals(type)) {
                if (TETHERUS.equals(transaction.get(PROPERTYID).toString())) {
                    usdtTransaction = JSON.parseObject(JSON.toJSONString(info), USDTTransaction.class);
                }
            }
            if (usdtTransaction != null) {
                usdtTransaction.setFormatTimeStamp(dateTransform.getTimeStampToUTC(usdtTransaction.getBlockTimeStamp()));
            }
        }
        return usdtTransaction;
    }

}
