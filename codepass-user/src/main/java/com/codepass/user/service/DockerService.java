package com.codepass.user.service;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class DockerService {
    public String createDocker() {
        return new Random().toString();
    }

    public String cloneDocker(String dockerId) {
        return new Random().toString();
    }

    public boolean mountDocker(String dockerId) {
        return true;
    }

    public boolean umountDocker(String dockerId) {
        return true;
    }
}
