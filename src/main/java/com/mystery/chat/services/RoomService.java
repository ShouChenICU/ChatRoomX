package com.mystery.chat.services;

import com.mystery.chat.costant.MemberRoles;
import com.mystery.chat.entities.RoomEntity;
import com.mystery.chat.exceptions.BusinessException;
import com.mystery.chat.mappers.RoomMapper;
import com.mystery.chat.vos.MemberVO;
import com.mystery.chat.vos.RoomVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author shouchen
 * @date 2022/12/6
 */
@Service
public class RoomService {
    private RoomMapper roomMapper;
    private MemberService memberService;

    /**
     * 通过房间ID查询房间
     *
     * @param roomID 房间ID
     * @return 房间实体
     */
    public Optional<RoomEntity> getByID(String roomID) {
        return roomMapper.getByID(roomID);
    }

    /**
     * 添加房间
     *
     * @param roomEntity 房间
     */
    @Transactional(rollbackFor = Exception.class)
    public void addRoom(RoomEntity roomEntity) {

    }

    /**
     * 根据房间ID查询房间
     *
     * @param roomID 房间ID
     * @return 房间VO
     */
    @Transactional(rollbackFor = Exception.class)
    public RoomVO getRoomVOByID(String roomID) {
        return new RoomVO(getByID(roomID)
                .orElseThrow(() -> new BusinessException("Room not found")))
                .setMembers(memberService
                        .listByRoomID(roomID)
                        .stream()
                        .sorted((a, b) -> MemberRoles.calcWeight(b.getRole())
                                - MemberRoles.calcWeight(a.getRole())
                                + Long.compare(a.getJoinInstant(), b.getJoinInstant()))
                        .map(MemberVO::new)
                        .collect(Collectors.toList())
                );
    }

    @Autowired
    public RoomService setRoomMapper(RoomMapper roomMapper) {
        this.roomMapper = roomMapper;
        return this;
    }

    @Autowired
    public RoomService setMemberService(MemberService memberService) {
        this.memberService = memberService;
        return this;
    }
}
