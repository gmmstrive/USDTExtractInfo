package com.gikee.usdtcollect.dao;

import com.gikee.usdtcollect.model.USDTTransaction;
import com.gikee.usdtcollect.utils.MongoDBDao;
import org.springframework.stereotype.Repository;

@Repository
public class USDTTransactionDao extends MongoDBDao<USDTTransaction> {

    @Override
    protected Class<USDTTransaction> getEntityClass() {
        return USDTTransaction.class;
    }
}
