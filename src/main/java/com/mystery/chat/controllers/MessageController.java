package com.mystery.chat.controllers;

import com.mystery.chat.entities.MessageEntity;
import com.mystery.chat.exceptions.BusinessException;
import com.mystery.chat.services.MessageService;
import com.mystery.chat.vos.MessageVO;
import com.mystery.chat.vos.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author shouchen
 * @date 2022/12/21
 */
@RestController
@RequestMapping("/api/msg")
public class MessageController {
    private MessageService messageService;

    /**
     * 发送文本消息
     *
     * @param authentication authentication
     * @param message        消息
     * @return 结果
     */
    @PreAuthorize("@memberService.userIsInRoom(#authentication.getName(), #message.roomID)")
    @PostMapping("/sendText")
    public ResultVO<?> sendText(
            Authentication authentication,
            @RequestBody @Validated MessageVO message) {
        messageService.sendText(new MessageEntity(message).setUid(authentication.getName()));
        return ResultVO.success();
    }

    /**
     * 查询消息列表
     *
     * @param authentication authentication
     * @param roomID         房间id
     * @param instant        时间戳
     * @param size           列表长度
     * @return 消息列表
     */
    @PreAuthorize("hasAnyRole(@roles.ADMIN) || @memberService.userIsInRoom(#authentication.getName(), #roomID)")
    @PostMapping("/list")
    public ResultVO<List<MessageVO>> listMessages(
            Authentication authentication,
            @RequestParam String roomID,
            @RequestParam(defaultValue = "0") long instant,
            @RequestParam(defaultValue = "10") int size) {
        if (instant < 0) {
            instant = 0;
        }
        if (size < 0) {
            throw new BusinessException("List size cannot below than 0");
        } else if (size > 100) {
            throw new BusinessException("List size cannot exceed 100");
        }
        return ResultVO.of(messageService.listMsgVOs(roomID, instant, size));
    }

    @Autowired
    public MessageController setMessageService(MessageService messageService) {
        this.messageService = messageService;
        return this;
    }
}
