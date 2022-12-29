package com.mystery.chat.services;

import com.mystery.chat.configures.AppConfig;
import com.mystery.chat.costant.AccountStatus;
import com.mystery.chat.costant.Roles;
import com.mystery.chat.entities.UserEntity;
import com.mystery.chat.exceptions.BusinessException;
import com.mystery.chat.mappers.UserMapper;
import com.mystery.chat.utils.LRUCache;
import com.mystery.chat.utils.UIDGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
public class UserService implements UserDetailsService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);
    private final PasswordEncoder passwordEncoder;
    private final LRUCache<String, Optional<UserEntity>> userCache;
    private UserMapper userMapper;

    public UserService(PasswordEncoder passwordEncoder, AppConfig appConfig) {
        this.passwordEncoder = passwordEncoder;
        this.userCache = new LRUCache<>(appConfig.userCacheSize);
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
            return getByUID((String) authentication.getPrincipal());
        }
        return Optional.empty();
    }

    /**
     * 根据用户UID查询用户
     *
     * @param uid uid
     * @return 用户
     */
    public Optional<UserEntity> getByUID(String uid) {
        return userCache.getElsePut(uid,
                () -> userMapper.getByUID(uid)
        );
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
                        .getPrincipal()
        );
        return uid;
    }

    /**
     * 更新用户
     *
     * @param userEntity 用户
     */
    public void updateUser(UserEntity userEntity) {
        if (userMapper.update(userEntity) == 0) {
            throw new BusinessException("User information update failure");
        }
        userCache.remove(userEntity.getUid());
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
    public UserService setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
        return this;
    }
}
