package com.gikee.usdtcollect.tasks;

import com.alibaba.fastjson.JSON;
import com.gikee.usdtcollect.model.MessageReminder;
import com.gikee.usdtcollect.model.USDTBlock;
import com.gikee.usdtcollect.model.USDTBlockInfo;
import com.gikee.usdtcollect.model.USDTTransaction;
import com.gikee.usdtcollect.service.USDTBlockService;
import com.gikee.usdtcollect.service.USDTTransactionService;
import com.gikee.usdtcollect.utils.KafkaSender;
import com.gikee.usdtcollect.utils.SignNumberFile;
import com.neovisionaries.ws.client.WebSocket;
import groovy.lang.Tuple6;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class ExtractUSDTTasks {

    @Autowired
    private WebSocket ws;

    @Autowired
    private SignNumberFile signNumberFile;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private KafkaSender<USDTBlock> kafkaSenderBlock;

    @Autowired
    private KafkaSender<USDTTransaction> kafkaSenderTransaction;

    @Autowired
    private USDTBlockService usdtBlockService;

    @Autowired
    private USDTTransactionService usdtTransactionService;

    @Value(value = "${spring.kafka.blockTopic}")
    private String blockTopic;

    @Value(value = "${spring.kafka.transactionTopic}")
    private String transactionTopic;

    //@Scheduled(initialDelay = 1000 * 60, fixedRate = 1000 * 60 * 10)
    public void extractBLockInfo() {
        // 当前块高度
        Integer blockNumber = signNumberFile.getBlockSignNumber() + 1;
        // 最新块高度
        Integer lastBlockNumber = usdtBlockService.getLastBlockNumber();
        lastBlockNumber=572128;
        if (lastBlockNumber != null) {
            while (blockNumber <= lastBlockNumber) {
                // 获取当前块高度信息
                USDTBlockInfo blockInfo = usdtBlockService.getUSDTBlockInfo(blockNumber);
                // 获取整合后交易集合以及块汇总信息 tuple 元组
                Tuple6<List<USDTTransaction>, List<MessageReminder>, String, String, String, String> tuples
                        = usdtTransactionService.getUSDTTransaction(blockNumber);
                // 获取整合后块信息
                USDTBlock block = usdtBlockService.getUSDTBlock(blockInfo, tuples.getThird(), tuples.getFourth(), tuples.getFifth(), tuples.getSixth());
                // 获取整合后交易集合
                List<USDTTransaction> txs = tuples.getFirst();
                // 获取大额异动集合
                List<MessageReminder> mrs = tuples.getSecond();
//                // 保存块信息
//                usdtBlockService.insertCollection(block);
//                // 保存交易信息
//                usdtTransactionService.insertCollection(txs);
                // 如果有大额交易发布到 websocket
                if (!mrs.isEmpty()) {
                    for (MessageReminder message : mrs) {
                        log.info("发布大额异动提醒,当前块高度为 : ---------------> {} <---------------", blockNumber);
                        // 发布到 redis
                        //redisTemplate.convertAndSend("TxChangeInfoTest", JSON.parse(obj.toString()));
                        ws.sendText(JSON.toJSONString(message));
                    }
                }
//                kafkaSenderBlock.kafkaSend(blockTopic, block);
//                if (!txs.isEmpty()) {
//                    kafkaSenderTransaction.kafkaSend(transactionTopic, txs);
//                }
                signNumberFile.saveBlockSignNumber(blockNumber);
                blockNumber = blockNumber + 1;
            }
        }
    }

}
