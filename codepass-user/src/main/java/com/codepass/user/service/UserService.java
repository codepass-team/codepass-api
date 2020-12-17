package com.codepass.user.service;

import com.codepass.user.dao.FollowUserRepository;
import com.codepass.user.dao.UserRepository;
import com.codepass.user.dao.entity.FollowUserEntity;
import com.codepass.user.dao.entity.UserEntity;
import com.codepass.user.dto.UserRegisterParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    FollowUserRepository followUserRepository;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    public UserEntity createNewUser(UserRegisterParam user) {
        UserEntity newUser = new UserEntity();
        newUser.setEmail(user.getEmail());
        newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
        newUser.setFollowerCount(0);
        userRepository.save(newUser);
        return newUser;
    }

    public FollowUserEntity followUser(int followerId, int beFollowerId) {
        if (followerId == beFollowerId) {
            throw new RuntimeException("Cannot follow self");
        }
        FollowUserEntity followUserEntity = new FollowUserEntity();
        followUserEntity.setFollowUser(followerId);
        followUserEntity.setBeFollowedUser(beFollowerId);
        followUserRepository.save(followUserEntity);
        return followUserEntity;
    }
}
