package com.mystery.chat.services;

import com.mystery.chat.entities.UserEntity;
import com.mystery.chat.exceptions.BusinessException;
import com.mystery.chat.mappers.UserMapper;
import com.mystery.chat.utils.UIDGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用户服务
 *
 * @author shouchen
 * @date 2022/11/27
 */
@Service
public class UserService implements UserDetailsService {
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
        UserEntity userEntity = userMapper.getByUID(uid);
        if (userEntity == null) {
            throw new BusinessException("User not found");
        }
        return userEntity;
    }

    /**
     * 注册用户
     *
     * @param userEntity 用户实体
     */
    @Transactional(rollbackFor = Exception.class)
    public void registerUser(UserEntity userEntity) {
        String uid = UIDGenerator.randomUID();

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
