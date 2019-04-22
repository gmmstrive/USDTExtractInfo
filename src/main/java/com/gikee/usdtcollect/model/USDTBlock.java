package com.gikee.usdtcollect.model;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Data
@Document(collection = "USDTBlock")
public class USDTBlock extends USDTBlockInfo implements Serializable {
    private String transactionCount = "0";                      // 交易总数
    private String validCount = "0";                            // 成功交易数量
    private String inValidCount = "0";                          // 失败交易数量
    private String transactionValue = "0";                      // 交易总值
    private String formatTimeStamp;                             // 格式化后时间戳
}