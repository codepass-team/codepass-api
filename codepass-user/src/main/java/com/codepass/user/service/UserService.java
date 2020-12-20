package com.codepass.user.service;

import com.codepass.user.dao.FollowUserRepository;
import com.codepass.user.dao.UserRepository;
import com.codepass.user.dao.entity.FollowUserEntity;
import com.codepass.user.dao.entity.UserEntity;
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

    public UserEntity updateUser(int userId, String nickname, String gender, String job, String tech, Integer age) {
        UserEntity userEntity = userRepository.findById(userId).orElseGet(UserEntity::new);
        if (nickname != null) userEntity.setNickname(nickname);
        if (gender != null) userEntity.setGender(gender);
        if (job != null) userEntity.setJob(job);
        if (tech != null) userEntity.setTech(tech);
        if (age != null) userEntity.setAge(age);
        userRepository.refresh(userRepository.save(userEntity));
        return userEntity;
    }

    public UserEntity getUserByNickname(String nickname) {
        return userRepository.findByNickname(nickname);
    }

    public UserEntity getUserById(int id) {
        return userRepository.findById(id).get();
    }

    public UserEntity createNewUser(String nickname, String password) {
        UserEntity newUser = new UserEntity();
        newUser.setNickname(nickname);
        newUser.setPassword(bcryptEncoder.encode(password));
        newUser.setFollowerCount(0);
        newUser = userRepository.saveAndFlush(newUser);
        return newUser;
    }

    public void followUser(int followerId, int beFollowerId) {
        if (followerId == beFollowerId) {
            throw new RuntimeException("Cannot follow self");
        }
        FollowUserEntity followUserEntity = new FollowUserEntity();
        followUserEntity.setFollowUser(followerId);
        followUserEntity.setBeFollowedUser(beFollowerId);
        followUserRepository.save(followUserEntity);
    }

    public void notFollowUser(int followerId, int beFollowerId) {
        FollowUserEntity followUserEntity = new FollowUserEntity();
        followUserEntity.setFollowUser(followerId);
        followUserEntity.setBeFollowedUser(beFollowerId);
        followUserRepository.delete(followUserEntity);
    }
}
