package com.codepass.user.service;

import com.codepass.user.dao.FollowUserRepository;
import com.codepass.user.dao.UserRepository;
import com.codepass.user.dao.entity.FollowUserEntity;
import com.codepass.user.dao.entity.UserEntity;
import com.codepass.user.dto.UserDTO;
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

    public UserEntity createNewUser(UserDTO user) {
        UserEntity newUser = new UserEntity();
        newUser.setEmail(user.getEmail());
//        newUser.setPassword(user.getPassword());
        newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
        newUser.setNickname(user.getNickname());
        return userRepository.save(newUser);
    }

    public FollowUserEntity followUser(int followerId, int beFollowerId) {
        if (followerId == beFollowerId) {
            throw new RuntimeException("Cannot follow self");
        }
        FollowUserEntity followUserEntity = new FollowUserEntity();
        followUserEntity.setFollowUser(followerId);
        followUserEntity.setBeFollowedUser(beFollowerId);
        return followUserRepository.save(followUserEntity);
    }
}
