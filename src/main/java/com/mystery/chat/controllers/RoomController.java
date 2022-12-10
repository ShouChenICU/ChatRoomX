package com.mystery.chat.controllers;

import com.mystery.chat.costant.ValidGroup;
import com.mystery.chat.entities.RoomEntity;
import com.mystery.chat.services.RoomService;
import com.mystery.chat.vos.ResultVO;
import com.mystery.chat.vos.RoomVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    /**
     * 添加房间
     *
     * @param roomVO 房间
     * @return 结果
     */
    @PostMapping("/add")
    @PreAuthorize("hasAnyRole(@roles.USER)")
    public ResultVO<?> addRoom(@RequestBody @Validated(ValidGroup.Insert.class) RoomVO roomVO) {
        roomService.addRoom(new RoomEntity(roomVO));
        return ResultVO.success();
    }

    @Autowired
    public RoomController setRoomService(RoomService roomService) {
        this.roomService = roomService;
        return this;
    }
}
