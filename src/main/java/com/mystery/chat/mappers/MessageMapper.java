package com.mystery.chat.mappers;

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

    int getMaxID();

    /**
     * 列出消息列表
     *
     * @param roomID  房间ID
     * @param instant 时间戳
     * @param size    长度
     * @return 消息列表
     */
    List<MessageVO> listMsgVOs(
            @Param("roomID") String roomID,
            @Param("instant") long instant,
            @Param("size") int size);
}
