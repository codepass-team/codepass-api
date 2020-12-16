package com.codepass.user.service;

import com.codepass.user.dto.UserDTO;
import com.codepass.user.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.ArrayList;

@Service
public class JwtUserDetailsService implements UserDetailsService {
    @Autowired
    EntityManager entityManager;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = getUserByEmail(email);
        if (userEntity != null) {
            return new User(userEntity.getEmail(), userEntity.getPassword(), new ArrayList<>());
        } else {
            throw new UsernameNotFoundException("User not found with username: " + email);
        }
    }

    public UserEntity getUserByEmail(String email) {
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(email);
        return entityManager.find(UserEntity.class, userEntity);
    }

    public UserEntity save(UserDTO user) {
        UserEntity newUser = new UserEntity();
        newUser.setEmail(user.getEmail());
        newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
        entityManager.persist(newUser);
        return newUser;
    }
}
