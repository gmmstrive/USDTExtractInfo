package com.gikee.usdtcollect.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Configuration
public class DateTransform {

    // UTC format
    @Value("${formatDate.formatUTC}")
    private String FormatUTC;

    // CST format
    @Value("${formatDate.formatCST}")
    private String FormatCST;

    /**
     * 通过时间戳转换为指定格式 UTC 时间
     *
     * @param timeStamp
     * @return
     */
    public String getTimeStampToUTC(String timeStamp) {
        if (timeStamp != null) {
            return LocalDateTime.parse(Instant.ofEpochSecond(Long.valueOf(timeStamp)).toString(), DateTimeFormatter.ofPattern(FormatUTC))
                    .format(DateTimeFormatter.ofPattern(FormatCST));
        } else {
            return "";
        }
    }

}
