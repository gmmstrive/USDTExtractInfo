package com.gikee.usdtcollect.service;

import com.gikee.usdtcollect.model.MessageReminder;
import com.gikee.usdtcollect.model.USDTTransaction;
import groovy.lang.Tuple6;

import java.util.List;
import java.util.Map;

public interface USDTTransactionService {

    /**
     * 批量插入
     * @param USDTTransactions
     * @return
     */
    int insertCollection(List<USDTTransaction> USDTTransactions);

    /**
     * 返回交易集合以及块汇总信息
     * @param blockNumber
     * @return
     */
    Tuple6<List<USDTTransaction>, List<MessageReminder>, String, String, String, String> getUSDTTransaction(Integer blockNumber);

    /**
     * 获取链上交易信息，整合成交易信息
     * @param transaction
     * @return
     */
    USDTTransaction getMapToInstance(Map<String, Object> transaction);

}
