package com.mystery.chat.services;

import com.mystery.chat.entities.UserEntity;
import com.mystery.chat.exceptions.BusinessException;
import com.mystery.chat.mappers.UserMapper;
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
    private UserMapper userMapper;

    public UserService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * 根据uid查询用户实体
     *
     * @param uid uid
     * @return 用户
     */
    public UserEntity getByUID(String uid) {
        return userMapper.getByUID(uid);
    }

    /**
     * 注册用户
     *
     * @param userEntity 用户实体
     */
    @Transactional(rollbackFor = Exception.class)
    public void registerUser(UserEntity userEntity) {
        if (userMapper.getByEmail(userEntity.getEmail()) != null) {
            throw new BusinessException("该Email已被注册");
        }
        String uid;
        do {
            uid = UIDGenerator.randomUID();
        } while (getByUID(uid) != null);
        userEntity.setUid(uid)
                .setCreateInstant(System.currentTimeMillis())
                .setPassword(passwordEncoder
                        .encode(Objects.requireNonNullElse(userEntity.getPassword(), ""))
                );
        if (userMapper.addUser(userEntity) == 0) {
            throw new BusinessException("注册失败");
        }
        LOGGER.info("注册用户 UID {} 注册人 {}",
                uid,
                SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getPrincipal()
        );
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return User.builder()
                .username("qwe")
                .password("asd")
                .passwordEncoder(passwordEncoder::encode)
                .authorities("USER")
                .build();
    }

    @Autowired
    public UserService setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
        return this;
    }
}
