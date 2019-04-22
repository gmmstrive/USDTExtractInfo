package com.gikee.usdtcollect.model;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

@Data
public class MessageReminder {
    private String symbol = "USDT";
    @JSONField(name = "data")
    private USDTMessage usdtMessage;

    public MessageReminder(USDTMessage usdtMessage) {
        this.usdtMessage = usdtMessage;
    }
}