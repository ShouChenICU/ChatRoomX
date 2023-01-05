package com.mystery.chat.configures;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author shouchen
 * @date 2022/12/11
 */
@PropertySource("file:app.conf")
@Configuration
public class AppConfig {
    @Value("${maxRoomsForUser:10}")
    public int maxRoomsForUser;
    @Value("${userCacheSize:255}")
    public int userCacheSize;
    @Value("${broadcastThreadPollSize:16}")
    public int broadcastThreadPollSize;
    @Value("${globalRoomMaxMsgCount:128}")
    public int globalRoomMaxMsgCount;
}
