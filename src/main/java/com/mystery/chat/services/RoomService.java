package com.mystery.chat.services;

import com.mystery.chat.entities.RoomEntity;
import com.mystery.chat.mappers.RoomMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author shouchen
 * @date 2022/12/6
 */
@Service
public class RoomService {
    private RoomMapper roomMapper;

    /**
     * 通过房间ID查询房间
     *
     * @param id 房间ID
     * @return 房间实体
     */
    public RoomEntity getByID(String id) {
        return roomMapper.getByID(id);
    }

    @Autowired
    public RoomService setRoomMapper(RoomMapper roomMapper) {
        this.roomMapper = roomMapper;
        return this;
    }
}
