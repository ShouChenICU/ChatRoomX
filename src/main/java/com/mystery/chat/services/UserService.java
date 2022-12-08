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
    private final LRUCache<String, UserEntity> userCache;
    private UserMapper userMapper;

    public UserService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
        this.userCache = new LRUCache<>(255);
    }

    /**
     * 根据用户UID查询用户
     *
     * @param uid uid
     * @return 用户
     */
    public Optional<UserEntity> getByUID(String uid) {
        UserEntity userEntity = userCache.get(uid);
        if (userEntity == null) {
            Optional<UserEntity> user = userMapper.getByUID(uid);
            user.ifPresent(entity -> userCache.put(uid, entity));
            return user;
        }
        return Optional.of(userEntity);
    }

    /**
     * 添加用户
     *
     * @param userEntity 用户实体
     */
    @Transactional(rollbackFor = Exception.class)
    public void addUser(UserEntity userEntity) {
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
                .setCreateInstant(System.currentTimeMillis())
                .setPassword(passwordEncoder
                        .encode(Objects.requireNonNullElse(userEntity.getPassword(), ""))
                )
                .setRole(Roles.ROLE_USER);
        if (userMapper.addUser(userEntity) == 0) {
            throw new BusinessException("Add failure");
        }
        userCache.put(uid, userEntity);
        LOGGER.info("Add user UID {} by {}",
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
                .authorities(userEntity.getRole())
                .build();
    }

    @Autowired
    public UserService setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
        return this;
    }
}
