package com.mystery.chat.services;

import com.mystery.chat.configures.AppConfig;
import com.mystery.chat.costant.MemberRoles;
import com.mystery.chat.entities.MemberEntity;
import com.mystery.chat.entities.RoomEntity;
import com.mystery.chat.entities.UserEntity;
import com.mystery.chat.exceptions.BusinessException;
import com.mystery.chat.mappers.RoomMapper;
import com.mystery.chat.utils.UIDGenerator;
import com.mystery.chat.vos.RoomVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author shouchen
 * @date 2022/12/6
 */
@Service
public class RoomService {
    private RoomMapper roomMapper;
    private UserService userService;
    private MemberService memberService;
    private MessageService messageService;
    private AppConfig appConfig;

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
    public String addRoom(RoomEntity roomEntity) {
        UserEntity userEntity = userService.me().orElseThrow(() -> new BusinessException("User not found"));
        if (roomMapper.countByUID(userEntity.getUid()) >= appConfig.maxRoomsForUser) {
            throw new BusinessException("You have created the maximum number of " + appConfig.maxRoomsForUser + " rooms");
        }
        String id;
        do {
            id = UIDGenerator.randomUID();
        } while (getByID(id).isPresent());
        roomEntity.setId(id).setCreateInstant(System.currentTimeMillis());
        roomMapper.insert(roomEntity);
        memberService.addMember(new MemberEntity()
                .setUid(userEntity.getUid())
                .setRoomID(id)
                .setRole(MemberRoles.OWNER)
                .setLabel("")
                .setJoinInstant(System.currentTimeMillis()));
        return id;
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
                .setMembers(
                        memberService.listVOsByRoomID(roomID)
                )
                .setMessages(
                        messageService.listMsgVOs(roomID, 0, 0, 10)
                );
    }

    /**
     * 查询指定用户加入的房间列表
     *
     * @return 房间列表
     */
    @Transactional(rollbackFor = Exception.class)
    public List<RoomVO> listRoomVOsByUID(String uid) {
        return roomMapper.listRoomsByUID(uid)
                .stream()
                .map(roomEntity -> new RoomVO(roomEntity)
                        .setMembers(
                                new ArrayList<>(0)
                        )
                        .setMessages(
                                messageService.listMsgVOs(roomEntity.getId(), 0, 0, 10)
                        ))
                .collect(Collectors.toList());
    }

    @Autowired
    public RoomService setRoomMapper(@Lazy RoomMapper roomMapper) {
        this.roomMapper = roomMapper;
        return this;
    }

    @Autowired
    public RoomService setUserService(@Lazy UserService userService) {
        this.userService = userService;
        return this;
    }

    @Autowired
    public RoomService setMemberService(@Lazy MemberService memberService) {
        this.memberService = memberService;
        return this;
    }

    @Autowired
    public RoomService setMessageService(@Lazy MessageService messageService) {
        this.messageService = messageService;
        return this;
    }

    @Autowired
    public RoomService setAppConfig(@Lazy AppConfig appConfig) {
        this.appConfig = appConfig;
        return this;
    }
}
