package com.gikee.usdtcollect.controller;

import com.gikee.usdtcollect.tasks.ExtractUSDTTasks;
import com.gikee.usdtcollect.utils.SignNumberFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class USDTController {

    @Autowired
    private SignNumberFile signNumberFile;

    @Autowired
    private ExtractUSDTTasks extractUSDTTasks;

    @RequestMapping(value = "/initSignFile")
    public String initSignFile(Integer blockNumber) {
        signNumberFile.initSignFile(blockNumber);
        return "初始化块号成功 : " + blockNumber;
    }

    @RequestMapping(value = "/insertCollection")
    public void insertCollection() {
        extractUSDTTasks.extractBLockInfo();
    }

}
