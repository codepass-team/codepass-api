package com.codepass.user;

import com.codepass.user.dao.CustomRepositoryImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(repositoryBaseClass = CustomRepositoryImpl.class)
public class CodepassApplication {

    public static void main(String[] args) {
        SpringApplication.run(CodepassApplication.class, args);
    }


}
