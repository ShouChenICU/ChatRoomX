package com.mystery.chat.services;

import com.mystery.chat.costant.AccountStatus;
import com.mystery.chat.costant.Roles;
import com.mystery.chat.entities.UserEntity;
import com.mystery.chat.exceptions.BusinessException;
import com.mystery.chat.mappers.UserMapper;
import com.mystery.chat.utils.UIDGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

/**
 * 用户服务
 *
 * @author shouchen
 * @date 2022/11/27
 */
@Service
@CacheConfig(cacheNames = "USER_CACHE")
public class UserService implements UserDetailsService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);
    private final PasswordEncoder passwordEncoder;
    private UserMapper userMapper;

    public UserService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * 获取当前登陆的用户信息
     *
     * @return 当前登陆的用户信息
     */
    public Optional<UserEntity> me() {
        Authentication authentication = SecurityContextHolder
                .getContext().getAuthentication();
        if (authentication != null) {
            return getByUID(authentication.getName());
        }
        return Optional.empty();
    }

    /**
     * 根据用户UID查询用户
     *
     * @param uid uid
     * @return 用户
     */
    @Cacheable(key = "#uid")
    public Optional<UserEntity> getByUID(String uid) {
        return userMapper.getByUID(uid);
    }

    /**
     * 根据邮箱查询用户
     *
     * @param email 用户邮箱
     * @return 用户
     */
    public Optional<UserEntity> getByEmail(String email) {
        return userMapper.getByEmail(email);
    }

    /**
     * 添加用户
     *
     * @param userEntity 用户实体
     * @return 用户UID
     */
    @Transactional(rollbackFor = Exception.class)
    public String addUser(UserEntity userEntity) {
        userMapper.getByEmail(userEntity.getEmail())
                .ifPresent(entity -> {
                    throw new BusinessException("Email already exists");
                });
        String uid;
        // 生成UID
        do {
            uid = UIDGenerator.randomUID();
        } while (getByUID(uid).isPresent());
        // 初始化
        userEntity.setUid(uid)
                .setPassword(passwordEncoder
                        .encode(Objects.requireNonNullElse(userEntity.getPassword(), ""))
                )
                .setRole(Roles.ROLE_EMPTY)
                .setStatus(AccountStatus.INACTIVE)
                .setCreateInstant(System.currentTimeMillis());
        if (userMapper.insert(userEntity) == 0) {
            throw new BusinessException("Add failure");
        }
        LOGGER.info("Add user UID {} by {}",
                uid,
                SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getName()
        );
        return uid;
    }

    /**
     * 更新用户
     *
     * @param userEntity 用户
     */
    @CacheEvict(key = "#userEntity.uid")
    public void updateUser(UserEntity userEntity) {
        if (userMapper.update(userEntity) == 0) {
            throw new BusinessException("User information update failure");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userMapper
                .getByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return User.builder()
                .username(userEntity.getUid())
                .password(userEntity.getPassword())
                .authorities(
                        AccountStatus.NORMAL.equalsIgnoreCase(userEntity.getStatus())
                                ? userEntity.getRole()
                                : Roles.ROLE_EMPTY
                )
                .build();
    }

    @Autowired
    public UserService setUserMapper(@Lazy UserMapper userMapper) {
        this.userMapper = userMapper;
        return this;
    }
}
