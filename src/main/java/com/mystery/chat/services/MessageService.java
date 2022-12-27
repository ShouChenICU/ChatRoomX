package com.mystery.chat.services;

import com.mystery.chat.entities.MessageEntity;
import com.mystery.chat.mappers.MessageMapper;
import com.mystery.chat.utils.DateTimeFormatUtils;
import com.mystery.chat.vos.MessageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

/**
 * @author shouchen
 * @date 2022/12/21
 */
@Service
public class MessageService {
    private final AtomicLong genKey;
    private MessageMapper messageMapper;

    public MessageService() {
        genKey = new AtomicLong();
    }

    public void sendMsg(MessageEntity messageEntity) {

    }

    /**
     * 查询消息列表
     *
     * @param roomID  房间ID
     * @param instant 时间戳
     * @param size    长度
     * @return 消息列表
     */
    public List<MessageVO> listMsgVOs(String roomID, long instant, int size) {
        return messageMapper.listMsgVOs(roomID, instant, size)
                .stream()
                .map(msg -> msg.setDateTime(DateTimeFormatUtils.formatMsg(msg.getInstant())))
                .collect(Collectors.toList());
    }

    @Autowired
    public MessageService setMessageMapper(MessageMapper messageMapper) {
        this.messageMapper = messageMapper;
        genKey.set(messageMapper.getMaxID());
        return this;
    }
}
