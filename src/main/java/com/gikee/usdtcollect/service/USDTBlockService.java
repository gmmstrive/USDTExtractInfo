package com.gikee.usdtcollect.service;

import com.gikee.usdtcollect.model.USDTBlock;
import com.gikee.usdtcollect.model.USDTBlockInfo;

public interface USDTBlockService {

    /**
     * 插入单条
     *
     * @param usdtBlock
     */
    void insertCollection(USDTBlock usdtBlock);

    /**
     * 获取最新块号
     *
     * @return
     */
    Integer getLastBlockNumber();

    /**
     * 获取当前链上块信息
     *
     * @param blockNumber
     * @return
     */
    USDTBlockInfo getUSDTBlockInfo(Integer blockNumber);

    /**
     * 整合成块信息
     *
     * @param blockInfo
     * @param transactionCount
     * @param transactionValue
     * @param validCount
     * @param inValidCount
     * @return
     */
    USDTBlock getUSDTBlock(USDTBlockInfo blockInfo, String transactionCount, String transactionValue, String validCount, String inValidCount);

}
