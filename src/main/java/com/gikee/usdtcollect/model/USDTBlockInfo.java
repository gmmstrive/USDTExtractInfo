package com.gikee.usdtcollect.model;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class USDTBlockInfo implements Serializable {
    @JSONField(name = "height")
    private String blockNumber;                                 // 块号
    @JSONField(name = "hash")
    private String blockHash;                                   // 块哈希
    @JSONField(name = "confirmations")
    private String confirmations;
    @JSONField(name = "strippedsize")
    private String strippedSize;
    @JSONField(name = "size")
    private String size;
    @JSONField(name = "weight")
    private String weight;
    @JSONField(name = "version")
    private String version;
    @JSONField(name = "versionHex")
    private String versionHex;
    @JSONField(name = "merkleroot")
    private String merKleRoot;
    @JSONField(name = "tx")
    private List<String> txs;
    @JSONField(name = "mediantime")
    private String medianTime;
    @JSONField(name = "nonce")
    private String nonce;
    @JSONField(name = "bits")
    private String bits;
    @JSONField(name = "difficulty")
    private String difficulty;
    @JSONField(name = "chainwork")
    private String chainWork;
    @JSONField(name = "previousblockhash")
    private String previousBlockHash;
    @JSONField(name = "nextblockhash")
    private String nextBlockHash;
    @JSONField(name = "time")
    private String blockTimeStamp;                              // 时间戳
}