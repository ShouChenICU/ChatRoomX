package com.mystery.chat.mappers;

import com.mystery.chat.entities.RoomEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

/**
 * @author shouchen
 * @date 2022/12/7
 */
@Mapper
public interface RoomMapper {

    /**
     * 通过房间ID查询房间
     *
     * @param id 房间ID
     * @return 房间实体
     */
    Optional<RoomEntity> getByID(@Param("id") String id);

    /**
     * 查询指定用户加入的房间列表
     *
     * @return 房间列表
     */
    List<RoomEntity> listRoomsByUID(@Param("uid") String uid);

    /**
     * 添加房间
     *
     * @param room 房间
     * @return 更新的条数
     */
    int insert(@Param("room") RoomEntity room);

    /**
     * 查询某人拥有的房间数量
     *
     * @param uid 用户UID
     * @return 房间数量
     */
    int countByUID(@Param("uid") String uid);
}
