package com.gikee.usdtcollect.model;

import lombok.Data;

@Data
public class TransactionAddress {
    private String address;

    public TransactionAddress(String address) {
        this.address = address;
    }
}
