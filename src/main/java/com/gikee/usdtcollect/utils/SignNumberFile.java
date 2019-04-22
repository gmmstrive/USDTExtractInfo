package com.gikee.usdtcollect.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.IOException;

@Slf4j
@Configuration
public class SignNumberFile {

    @Value(value = "${blockSignFile.path}")
    private String path;

    @Value(value = "${blockSignFile.fileName}")
    private String fileName;

    public void initSignFile(Integer blockNumber) {
        try {
            File dir = new File(path);
            if (!dir.exists()) {
                dir.mkdir();
                File file = new File(path + fileName);
                file.createNewFile();
                saveBlockSignNumber(blockNumber);
            } else {
                saveBlockSignNumber(blockNumber);
            }
            log.info("初始化块游标成功,当前块游标高度为 : ---------------> {} <---------------", blockNumber);
        } catch (IOException e) {
            log.info("初始化块游标失败");
            e.printStackTrace();
        }
    }

    public Integer getBlockSignNumber() {
        Integer blockNumber = 0;
        try {
            blockNumber = Integer.valueOf(FileUtils.readFileToString(new File(path + fileName), "UTF-8"));
            log.info("获取块游标成功,当前块游标高度为 : ---------------> {} <---------------", blockNumber);
        } catch (IOException e) {
            log.error("获取块游标失败,使用默认游标高度 0");
            e.printStackTrace();
        }
        return blockNumber;
    }

    public void saveBlockSignNumber(Integer blockNumber) {
        try {
            FileUtils.writeStringToFile(new File(path + fileName), String.valueOf(blockNumber), "UTF-8", false);
            log.info("保存块游标成功,当前块游标高度为 : ---------------> {} <---------------", blockNumber);
        } catch (IOException e) {
            log.error("保存块游标失败");
            e.printStackTrace();
        }

    }
}
