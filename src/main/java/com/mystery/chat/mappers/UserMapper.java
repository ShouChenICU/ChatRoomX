package com.mystery.chat.mappers;

import com.mystery.chat.entities.UserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Optional;

/**
 * @author shouchen
 * @date 2022/11/30
 */
@Mapper
public interface UserMapper {

    /**
     * 根据用户UID查询用户
     *
     * @param uid uid
     * @return 用户
     */
    Optional<UserEntity> getByUID(@Param("uid") String uid);

    /**
     * 根据Email查询用户
     *
     * @param email Email
     * @return 用户
     */
    Optional<UserEntity> getByEmail(@Param("email") String email);

    /**
     * 新增用户
     *
     * @param user 用户实体
     * @return 结果
     */
    int insert(@Param("user") UserEntity user);

    /**
     * 更新用户
     *
     * @param user 用户
     * @return 结果
     */
    int update(@Param("user") UserEntity user);
}
