package com.mystery.chat.services;

import com.mystery.chat.costant.Roles;
import com.mystery.chat.entities.UserEntity;
import com.mystery.chat.exceptions.BusinessException;
import com.mystery.chat.mappers.UserMapper;
import com.mystery.chat.utils.LRUCache;
import com.mystery.chat.utils.UIDGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * 用户服务
 *
 * @author shouchen
 * @date 2022/11/27
 */
@Service
public class UserService implements UserDetailsService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);
    private final PasswordEncoder passwordEncoder;
    private final LRUCache<String, UserEntity> userCache;
    private UserMapper userMapper;

    public UserService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
        this.userCache = new LRUCache<>(255);
    }

    /**
     * 根据uid查询用户实体
     *
     * @param uid uid
     * @return 用户
     */
    public UserEntity getByUID(String uid) {
        UserEntity userEntity = userCache.get(uid);
        if (userEntity == null) {
            userEntity = userMapper.getByUID(uid);
            if (userEntity != null) {
                userCache.put(uid, userEntity);
            }
        }
        return userEntity;
    }

    /**
     * 添加用户
     *
     * @param userEntity 用户实体
     */
    @Transactional(rollbackFor = Exception.class)
    public void addUser(UserEntity userEntity) {
        if (userMapper.getByEmail(userEntity.getEmail()) != null) {
            throw new BusinessException("该Email已被注册");
        }
        String uid;
        // 生成UID
        do {
            uid = UIDGenerator.randomUID();
        } while (getByUID(uid) != null);
        // 初始化
        userEntity.setUid(uid)
                .setCreateInstant(System.currentTimeMillis())
                .setPassword(passwordEncoder
                        .encode(Objects.requireNonNullElse(userEntity.getPassword(), ""))
                )
                .setRole(Roles.ROLE_USER);
        if (userMapper.addUser(userEntity) == 0) {
            throw new BusinessException("添加失败");
        }
        userCache.put(uid, userEntity);
        LOGGER.info("添加用户 UID {} 添加人 {}",
                uid,
                SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getPrincipal()
        );
    }

    /**
     * 更新用户
     *
     * @param userEntity 用户
     */
    public void updateUser(UserEntity userEntity) {
        if (userMapper.updateUser(userEntity) == 0) {
            throw new BusinessException("用户信息更新失败");
        }
        userCache.put(userEntity.getUid(), userEntity);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userMapper.getByEmail(username);
        if (userEntity == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return User.builder()
                .username(userEntity.getUid())
                .password(userEntity.getPassword())
                .authorities(userEntity.getRole())
                .build();
    }

    @Autowired
    public UserService setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
        return this;
    }
}
