package com.codepass.user.service;

import com.codepass.user.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;

@Component
@Transactional
public class UserService {
    @Autowired
    EntityManager entityManager;


}
