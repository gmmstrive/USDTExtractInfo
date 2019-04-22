package com.gikee.usdtcollect.service.impl;

import com.alibaba.fastjson.JSON;
import com.gikee.usdtcollect.dao.USDTBlockDao;
import com.gikee.usdtcollect.model.USDTBlock;
import com.gikee.usdtcollect.model.USDTBlockInfo;
import com.gikee.usdtcollect.service.USDTBlockService;
import com.gikee.usdtcollect.utils.DateTransform;
import com.googlecode.jsonrpc4j.JsonRpcHttpClient;
import foundation.omni.rpc.OmniCLIClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@Service
public class USDTBlockServiceImpl implements USDTBlockService {

    public final static String METHODGETBLOCKHASH = "getblockhash";
    public final static String METHODGETBLOCK = "getblock";

    @Autowired
    private OmniCLIClient omniCLIClient;

    @Autowired
    private JsonRpcHttpClient jsonRpcHttpClient;

    @Autowired
    private DateTransform dateTransform;

    @Autowired
    private USDTBlockDao usdtBlockDao;

    @Override
    public void insertCollection(USDTBlock usdtBlock) {
        usdtBlockDao.insertCollection(usdtBlock);
    }

    @Override
    public Integer getLastBlockNumber() {
        Integer lastBlockNumber = null;
        try {
            lastBlockNumber = omniCLIClient.getBlockCount();
            log.info("获取最新块高度成功,当前块高度为 : ---------------> {} <---------------", lastBlockNumber);
        } catch (IOException e) {
            e.printStackTrace();
            log.error("获取最新块高度失败");
        }
        return lastBlockNumber;
    }

    @Override
    public USDTBlockInfo getUSDTBlockInfo(Integer blockNumber) {
        String blockHash;
        Object obj;
        USDTBlockInfo blockInfo = null;
        try {
            blockHash = jsonRpcHttpClient.invoke(METHODGETBLOCKHASH, new Object[]{blockNumber}, String.class);
            obj = jsonRpcHttpClient.invoke(METHODGETBLOCK, new Object[]{blockHash, true}, Object.class);
            blockInfo = JSON.parseObject(JSON.toJSONString(obj), USDTBlockInfo.class);
            log.info("获取当前块高度信息成功,当前块高度为 : ---------------> {} <---------------", blockNumber);
        } catch (Throwable throwable) {
            log.error("获取当前块高度信息失败,当前块高度为 : ---------------> {} <---------------", blockNumber);
            try {
                log.warn("获取当前块高度信息失败,可能因为访问节点操作太频繁,开始休息 5 秒 : ---------------> {} <---------------" + blockNumber);
                Thread.sleep(5000);
                blockInfo = getUSDTBlockInfo(blockNumber);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            throwable.printStackTrace();
        }
        return blockInfo;
    }

    @Override
    public USDTBlock getUSDTBlock(USDTBlockInfo blockInfo, String transactionCount, String transactionValue, String validCount, String inValidCount) {
        USDTBlock usdtBlock = new USDTBlock();
        usdtBlock.setBlockNumber(blockInfo.getBlockNumber());
        usdtBlock.setBlockHash(blockInfo.getBlockHash());
        usdtBlock.setTransactionCount(transactionCount);
        usdtBlock.setTransactionValue(transactionValue);
        usdtBlock.setValidCount(validCount);
        usdtBlock.setInValidCount(inValidCount);
        usdtBlock.setConfirmations(blockInfo.getConfirmations());
        usdtBlock.setStrippedSize(blockInfo.getStrippedSize());
        usdtBlock.setSize(blockInfo.getSize());
        usdtBlock.setWeight(blockInfo.getWeight());
        usdtBlock.setVersion(blockInfo.getVersion());
        usdtBlock.setVersionHex(blockInfo.getVersionHex());
        usdtBlock.setMerKleRoot(blockInfo.getMerKleRoot());
        usdtBlock.setMedianTime(blockInfo.getMedianTime());
        usdtBlock.setNonce(blockInfo.getNonce());
        usdtBlock.setBits(blockInfo.getBits());
        usdtBlock.setDifficulty(blockInfo.getDifficulty());
        usdtBlock.setChainWork(blockInfo.getChainWork());
        usdtBlock.setPreviousBlockHash(blockInfo.getPreviousBlockHash());
        usdtBlock.setNextBlockHash(blockInfo.getNextBlockHash());
        usdtBlock.setBlockTimeStamp(blockInfo.getBlockTimeStamp());
        usdtBlock.setFormatTimeStamp(dateTransform.getTimeStampToUTC(usdtBlock.getBlockTimeStamp()));
        return usdtBlock;
    }

}
