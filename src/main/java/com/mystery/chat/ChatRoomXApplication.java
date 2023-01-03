package com.mystery.chat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class ChatRoomXApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChatRoomXApplication.class, args);
    }

}
