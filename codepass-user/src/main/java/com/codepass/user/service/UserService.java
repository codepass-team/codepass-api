package com.codepass.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;

public class UserService {
    @Autowired
    HibernateTemplate hibernateTemplate;

    public boolean login(String email, String password){

        return true;
    }
}
