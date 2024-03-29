package com.mystery.chat.utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * 日期时间格式化工具
 *
 * @author shouchen
 */
public final class DateTimeFormatUtils {
    private static final DateTimeFormatter FORMATTER_0;
    private static final DateTimeFormatter FORMATTER_1;
    private static final DateTimeFormatter FORMATTER_2;
    private static final DateTimeFormatter FORMATTER_3;
    private static final ZoneId ZONE_ID;

    static {
        FORMATTER_0 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        FORMATTER_1 = DateTimeFormatter.ofPattern("H:mm");
        FORMATTER_2 = DateTimeFormatter.ofPattern("M-d H:mm");
        FORMATTER_3 = DateTimeFormatter.ofPattern("yyyy-MM-dd H:mm");
        ZONE_ID = ZoneId.systemDefault();
    }

    public static String format(long timestamp) {
        return FORMATTER_0.format(
                LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZONE_ID)
        );
    }

    public static String formatMsg(long timestamp) {
        LocalDateTime msgDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZONE_ID);
        LocalDateTime now = LocalDateTime.now(ZONE_ID);
        if (now.getYear() != msgDateTime.getYear()) {
            return FORMATTER_3.format(msgDateTime);
        } else if (msgDateTime.getMonthValue() != now.getMonthValue()
                || msgDateTime.getDayOfMonth() != now.getDayOfMonth()) {
            return FORMATTER_2.format(msgDateTime);
        } else {
            return FORMATTER_1.format(msgDateTime);
        }
    }
}
