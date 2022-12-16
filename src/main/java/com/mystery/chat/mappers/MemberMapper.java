package com.mystery.chat.mappers;

import com.mystery.chat.entities.MemberEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author shouchen
 * @date 2022/12/8
 */
@Mapper
public interface MemberMapper {

    /**
     * 根据房间ID查询成员列表
     *
     * @param roomID 房间ID
     * @return 成员列表
     */
    List<MemberEntity> listByRoomID(@Param("roomID") String roomID);

    /**
     * 添加成员
     *
     * @param member 成员
     * @return 更新的行数
     */
    int addMember(@Param("member") MemberEntity member);
}
