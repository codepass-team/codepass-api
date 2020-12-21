package com.codepass.user.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.Random;
import java.util.UUID;

@Component
public class DockerService {
    @Value("${docker.storage.path}")
    private String dockerStoragePath;

    public String createDocker() throws IOException {
        String dockerId = UUID.randomUUID().toString().replace("-", "").substring(0, 8);
        Files.createDirectory(
                Path.of(dockerStoragePath + dockerId),
                PosixFilePermissions.asFileAttribute(PosixFilePermissions.fromString("rwxrwxrwx"))
        );
        return dockerId;
    }

    public String cloneDocker(String dockerId) throws IOException {
        String newDockerId = createDocker();
        Files.copy(Path.of(dockerStoragePath + dockerId), Path.of(dockerStoragePath + newDockerId));
        return newDockerId;
    }

    public int mountDocker(String dockerId, String password) throws IOException {
        String filePath = dockerStoragePath + dockerId;
        String mountPath = "/home/codepass";
        int port = new Random().nextInt(10000) + 10000;
        Process process = Runtime.getRuntime().exec("timeout 1800 " +
                "docker run --env PASSWORD=" + password + " -it " +
                "-p 0.0.0.0:" + port + ":8080 " +
                "-v " + filePath + ":" + mountPath + " " +
                "codercom/code-server");
        return port;
    }

    public boolean umountDocker(String dockerId) {
        return true;
    }
}
