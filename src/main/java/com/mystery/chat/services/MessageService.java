package com.mystery.chat.services;

import com.mystery.chat.costant.MessageTypes;
import com.mystery.chat.entities.MessageEntity;
import com.mystery.chat.managers.ClientWebSocketSessionManager;
import com.mystery.chat.mappers.MessageMapper;
import com.mystery.chat.utils.DateTimeFormatUtils;
import com.mystery.chat.vos.MessageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

/**
 * @author shouchen
 * @date 2022/12/21
 */
@Service
public class MessageService {
    private final AtomicLong priKey;
    private UserService userService;
    private MessageMapper messageMapper;
    private MemberService memberService;
    private ClientWebSocketSessionManager sessionManager;

    public MessageService() {
        priKey = new AtomicLong();
    }

    /**
     * 发送文本消息
     *
     * @param messageEntity 消息
     */
    public void sendText(MessageEntity messageEntity) {
        messageEntity.setId(priKey.incrementAndGet())
                .setInstant(System.currentTimeMillis())
                .setType(MessageTypes.TEXT);
        messageMapper.insert(messageEntity);
        sessionManager.broadcastMsg(new MessageVO(messageEntity)
                .setSender(userService.getByUID(messageEntity.getUid())
                        .flatMap(userEntity -> Optional.ofNullable(userEntity.getNickname()))
                        .orElse(""))
                .setRole(memberService.get(messageEntity.getUid(), messageEntity.getRoomID())
                        .flatMap(memberEntity -> Optional.ofNullable(memberEntity.getRole()))
                        .orElse("")
                )
        );
    }

    /**
     * 查询消息列表
     *
     * @param roomID  房间ID
     * @param instant 时间戳
     * @param id      id
     * @param size    长度
     * @return 消息列表
     */
    public List<MessageVO> listMsgVOs(String roomID, long instant, long id, int size) {
        return messageMapper.listMsgVOs(roomID, instant, id, size)
                .stream()
                .map(msg -> msg.setDateTime(DateTimeFormatUtils.formatMsg(msg.getInstant())))
                .sorted(Comparator.comparingLong(MessageVO::getInstant))
                .collect(Collectors.toList());
    }

    /**
     * 获取房间的最新消息
     *
     * @param roomID 房间ID
     * @return 消息
     */
    public MessageVO latestMsgForRoom(String roomID) {
        return messageMapper.latestMsgForRoom(roomID);
    }

    @Autowired
    public MessageService setUserService(@Lazy UserService userService) {
        this.userService = userService;
        return this;
    }

    @Autowired
    public MessageService setMessageMapper(@Lazy MessageMapper messageMapper) {
        this.messageMapper = messageMapper;
        priKey.set(messageMapper.getMaxID());
        return this;
    }

    @Autowired
    public MessageService setMemberService(@Lazy MemberService memberService) {
        this.memberService = memberService;
        return this;
    }

    @Autowired
    public MessageService setSessionManager(@Lazy ClientWebSocketSessionManager sessionManager) {
        this.sessionManager = sessionManager;
        return this;
    }
}
