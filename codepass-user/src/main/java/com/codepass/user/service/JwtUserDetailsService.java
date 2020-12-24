package com.codepass.user.service;

import com.codepass.user.dao.UserRepository;
import com.codepass.user.dao.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByUsername(username);
        if (userEntity != null) {
            if (userEntity.getIsAdmin() == 1) {
                return User.builder()
                        .username(username)
                        .password(userEntity.getPassword())
                        .authorities("user", "admin")
                        .build();
            } else {
                return User.builder()
                        .username(username)
                        .password(userEntity.getPassword())
                        .authorities("user")
                        .build();
            }
        } else {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }
}
