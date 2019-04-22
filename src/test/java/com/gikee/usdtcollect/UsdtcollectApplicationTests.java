package com.gikee.usdtcollect;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gikee.usdtcollect.model.MessageReminder;
import com.gikee.usdtcollect.model.USDTBlock;
import com.gikee.usdtcollect.model.USDTBlockInfo;
import com.gikee.usdtcollect.model.USDTTransaction;
import com.gikee.usdtcollect.service.USDTBlockService;
import com.gikee.usdtcollect.service.USDTTransactionService;
import com.gikee.usdtcollect.tasks.ExtractUSDTTasks;
import groovy.lang.Tuple5;
import groovy.lang.Tuple6;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigInteger;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsdtcollectApplicationTests {


//    @Autowired
//    private USDTBlockService usdtBlockService;
//
//    @Autowired
//    private USDTTransactionService usdtTransactionService;
//
    @Autowired
    private ExtractUSDTTasks extractUSDTTasks;

    @Test
    public void extractBLockInfo(){
        extractUSDTTasks.extractBLockInfo();
    }
//
//    @Test
//    public void getUSDTBlockInfo() {
//        USDTBlockInfo usdtBlockInfo = usdtBlockService.getUSDTBlockInfo(568829);
//        USDTBlock usdtBlock = usdtBlockService.getUSDTBlock(usdtBlockInfo, "5", "6", "7", "8");
//        System.out.println(JSON.toJSONString(usdtBlock));
//        usdtBlockService.insertCollection(usdtBlock);
//    }
//
//    @Test
//    public void getUSDTTransaction() {
//        Tuple6<List<USDTTransaction>, List<MessageReminder>, String, String, String, String> tuples = usdtTransactionService.getUSDTTransaction(501039);
//        List<USDTTransaction> usdtTransactions = tuples.getFirst();
//        System.out.println(JSON.toJSONString(usdtTransactions));
//        List<MessageReminder> messageReminders = tuples.getSecond();
//
//        for (MessageReminder message : messageReminders) {
//            JSONObject obj = JSON.parseObject(JSON.toJSONString(message));
//            JSONObject jsonObj = obj.getJSONObject("usdtMessage");
//            obj.put("data",jsonObj);
//            obj.remove("usdtMessage");
//            System.out.println(obj.toJSONString());
//        }
//
//
//        System.out.println(JSON.toJSONString(messageReminders));
//        System.out.println(tuples.getThird());
//        System.out.println(tuples.getFourth());
//        System.out.println(tuples.getFifth());
//        System.out.println(tuples.getSixth());
//    }

}
