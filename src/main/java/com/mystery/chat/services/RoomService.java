package com.mystery.chat.services;

import com.mystery.chat.entities.RoomEntity;
import com.mystery.chat.exceptions.BusinessException;
import com.mystery.chat.mappers.RoomMapper;
import com.mystery.chat.vos.RoomVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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
    public Optional<RoomEntity> getByID(String id) {
        return roomMapper.getByID(id);
    }

    /**
     * 根据房间ID查询房间
     *
     * @param id 房间ID
     * @return 房间VO
     */
    @Transactional(rollbackFor = Exception.class)
    public RoomVO getRoomVOByID(String id) {
        RoomEntity roomEntity = getByID(id).orElseThrow(() -> new BusinessException("Room not found"));
        RoomVO roomVO = new RoomVO(roomEntity);
        // TODO: 2022/12/8  
        return roomVO;
    }

    @Autowired
    public RoomService setRoomMapper(RoomMapper roomMapper) {
        this.roomMapper = roomMapper;
        return this;
    }
}
