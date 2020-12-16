package com.codepass.user.dao;

import com.codepass.user.dao.entity.FollowUserEntity;
import com.codepass.user.dao.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface FollowUserRepository extends CrudRepository<FollowUserEntity, Integer> {
}
