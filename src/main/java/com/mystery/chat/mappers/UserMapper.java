package com.mystery.chat.mappers;

import com.mystery.chat.entities.UserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author shouchen
 * @date 2022/11/30
 */
@Mapper
public interface UserMapper {

    /**
     * 根据uid查询用户实体
     *
     * @param uid uid
     * @return 用户
     */
    UserEntity getByUID(@Param("uid") String uid);

    /**
     * 新增用户
     *
     * @param user 用户实体
     * @return 结果
     */
    int addUser(@Param("user") UserEntity user);
}
