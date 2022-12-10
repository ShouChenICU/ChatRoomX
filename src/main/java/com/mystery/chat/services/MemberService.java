package com.mystery.chat.services;

import com.mystery.chat.entities.MemberEntity;
import com.mystery.chat.mappers.MemberMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author shouchen
 * @date 2022/12/8
 */
@Service
public class MemberService {
    private MemberMapper memberMapper;

    /**
     * 根据房间ID查询成员列表
     *
     * @param roomID 房间ID
     * @return 成员列表
     */
    public List<MemberEntity> listByRoomID(String roomID) {
        return memberMapper.listByRoomID(roomID);
    }

    @Autowired
    public MemberService setMemberMapper(MemberMapper memberMapper) {
        this.memberMapper = memberMapper;
        return this;
    }
}
