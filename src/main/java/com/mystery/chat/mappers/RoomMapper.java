package com.mystery.chat.mappers;

import com.mystery.chat.entities.RoomEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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
}
