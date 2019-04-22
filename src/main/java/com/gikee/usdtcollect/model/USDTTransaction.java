package com.gikee.usdtcollect.model;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Data
@Document(collection = "USDTTransaction")
public class USDTTransaction implements Serializable {
    @JSONField(name = "block")
    private String blockNumber;
    @JSONField(name = "blockhash")
    private String blockHash;
    @JSONField(name = "txid")
    private String transactionHash;
    @JSONField(name = "sendingaddress")
    private String sendingAddress;
    @JSONField(name = "referenceaddress")
    private String referenceAddress;
    @JSONField(name = "amount")
    private String valueNumber;
    @JSONField(name = "fee")
    private String fee;
    @JSONField(name = "type")
    private String type;
    @JSONField(name = "type_int")
    private String typeInt;
    @JSONField(name = "propertyid")
    private String propertyID;
    private String propertyName = "TetherUS";
    @JSONField(name = "valid")
    private String valid;
    @JSONField(name = "divisible")
    private String divisible;
    @JSONField(name = "version")
    private String version;
    @JSONField(name = "positioninblock")
    private String positionInBlock;
    @JSONField(name = "confirmations")
    private String confirmations;
    @JSONField(name = "blocktime")
    private String blockTimeStamp;
    private String formatTimeStamp;
}
