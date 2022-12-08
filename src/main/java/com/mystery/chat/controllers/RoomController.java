package com.mystery.chat.controllers;

import com.mystery.chat.services.RoomService;
import com.mystery.chat.vos.ResultVO;
import com.mystery.chat.vos.RoomVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author shouchen
 * @date 2022/12/6
 */
@RestController
@RequestMapping("/api/room")
public class RoomController {
    private RoomService roomService;

    @PostMapping("/get")
    @PreAuthorize("hasAnyRole(@roles.USER)")
    public ResultVO<RoomVO> getByID(@RequestParam String id) {
        return ResultVO.of(roomService.getRoomVOByID(id));
    }

    @Autowired
    public RoomController setRoomService(RoomService roomService) {
        this.roomService = roomService;
        return this;
    }
}
