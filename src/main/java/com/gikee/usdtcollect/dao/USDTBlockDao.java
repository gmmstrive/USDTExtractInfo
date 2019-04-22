package com.gikee.usdtcollect.dao;

import com.gikee.usdtcollect.model.USDTBlock;
import com.gikee.usdtcollect.utils.MongoDBDao;
import org.springframework.stereotype.Repository;

@Repository
public class USDTBlockDao extends MongoDBDao<USDTBlock> {
    @Override
    protected Class<USDTBlock> getEntityClass() {
        return USDTBlock.class;
    }
}
