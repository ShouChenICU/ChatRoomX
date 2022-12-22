package com.mystery.chat.controllers;

import com.mystery.chat.exceptions.BusinessException;
import com.mystery.chat.services.MessageService;
import com.mystery.chat.services.UserService;
import com.mystery.chat.vos.MemberVO;
import com.mystery.chat.vos.MessageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author shouchen
 * @date 2022/12/21
 */
@RestController
@RequestMapping("/api/msg")
public class MessageController {
    private MessageService messageService;
    private UserService userService;

    @PostMapping("/list")
    public List<MessageVO> listMessages(
            @RequestParam String roomID,
            @RequestParam(defaultValue = "0") long instant,
            @RequestParam(defaultValue = "10") int size) {
        if (instant <= 0) {
            instant = System.currentTimeMillis();
        }
        if (size < 0) {
            throw new BusinessException("List size cannot below than 0");
        }

        // TODO: 2022/12/23
        return new ArrayList<>();
    }

    @Autowired
    public MessageController setMessageService(MessageService messageService) {
        this.messageService = messageService;
        return this;
    }
}
