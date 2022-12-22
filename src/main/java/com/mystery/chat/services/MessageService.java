package com.mystery.chat.services;

import com.mystery.chat.entities.MessageEntity;
import com.mystery.chat.mappers.MessageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author shouchen
 * @date 2022/12/21
 */
@Service
public class MessageService {
    private MessageMapper messageMapper;
    private AtomicLong genKey;

    public MessageService() {
        genKey = new AtomicLong();
    }

    public List<MessageEntity> listMessages(String roomID,long instant,int size) {

        return new ArrayList<>();
    }

    @Autowired
    public MessageService setMessageMapper(MessageMapper messageMapper) {
        this.messageMapper = messageMapper;
        genKey.set(messageMapper.getMaxID());
        return this;
    }
}
