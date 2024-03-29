package com.mystery.chat.mappers;

import com.mystery.chat.entities.MessageEntity;
import com.mystery.chat.vos.MessageVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author shouchen
 * @date 2022/12/21
 */
@Mapper
public interface MessageMapper {

    /**
     * 列出消息列表
     *
     * @param instant 时间戳
     * @param roomID  房间ID
     * @param size    长度
     * @return 消息列表
     */
    List<MessageVO> listMsgVOs(
            @Param("instant") long instant,
            @Param("roomID") String roomID,
            @Param("size") int size);

    /**
     * 查询房间的最新消息
     *
     * @param roomID 房间ID
     * @return 消息
     */
    MessageVO latestMsgForRoom(@Param("roomID") String roomID);

    /**
     * 插入一条消息
     *
     * @param msg 消息
     * @return 修改的行数
     */
    int insert(@Param("msg") MessageEntity msg);
}
