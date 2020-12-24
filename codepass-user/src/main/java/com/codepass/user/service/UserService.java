package com.codepass.user.service;

import com.codepass.user.dao.FollowUserRepository;
import com.codepass.user.dao.UserRepository;
import com.codepass.user.dao.entity.FollowUserEntity;
import com.codepass.user.dao.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;

@Component
@Transactional
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    FollowUserRepository followUserRepository;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    public UserEntity updateUser(int userId, String username, String gender, String job, String tech, Integer age) {
        UserEntity userEntity = userRepository.findById(userId).orElseGet(UserEntity::new);
        if (username != null) userEntity.setUsername(username);
        if (gender != null) userEntity.setGender(gender);
        if (job != null) userEntity.setJob(job);
        if (tech != null) userEntity.setTech(tech);
        if (age != null) userEntity.setAge(age);
        userRepository.save(userEntity);
        return userEntity;
    }

    public UserEntity getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public UserEntity getUserById(int id) {
        return userRepository.findById(id).get();
    }

    public UserEntity createNewUser(String username, String password) {
        UserEntity newUser = new UserEntity();
        newUser.setUsername(username);
        newUser.setPassword(bcryptEncoder.encode(password));
        newUser.setFollowerCount(0);
        userRepository.save(newUser);
        return newUser;
    }

    public void followUser(int followerId, int beFollowerId) {
        if (followerId == beFollowerId) {
            throw new RuntimeException("Cannot follow self");
        }
        FollowUserEntity followUserEntity = new FollowUserEntity();
        followUserEntity.setFollowUser(followerId);
        followUserEntity.setBeFollowedUser(beFollowerId);
        followUserEntity.setFollowTime(new Timestamp(System.currentTimeMillis()));
        followUserRepository.save(followUserEntity);
    }

    public void notFollowUser(int followerId, int beFollowerId) {
        FollowUserEntity followUserEntity = new FollowUserEntity();
        followUserEntity.setFollowUser(followerId);
        followUserEntity.setBeFollowedUser(beFollowerId);
        followUserRepository.delete(followUserEntity);
    }

    public Page<UserEntity> getAllUser(int page) {
        return userRepository.findAll(PageRequest.of(page, 10));
    }

    public void deleteUser(Integer userId) {
        userRepository.deleteById(userId);
    }
}
