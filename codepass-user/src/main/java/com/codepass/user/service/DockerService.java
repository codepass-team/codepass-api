package com.codepass.user.service;

import com.codepass.user.dao.DockerRepository;
import com.codepass.user.dao.entity.DockerEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.Random;
import java.util.UUID;

@Component
public class DockerService {
    @Value("${docker.storage.path}")
    private String dockerStoragePath;
    @Autowired
    DockerRepository dockerRepository;

    private static final Logger logger = LoggerFactory.getLogger(DockerService.class);

    public String createDocker(String parentId) throws IOException {
        String dockerId = UUID.randomUUID().toString().replace("-", "").substring(0, 8);
        Files.createDirectory(
                Path.of(dockerStoragePath + dockerId),
                PosixFilePermissions.asFileAttribute(PosixFilePermissions.fromString("rwxrwxrwx"))
        );
        DockerEntity dockerEntity = new DockerEntity();
        dockerEntity.setId(dockerId);
        dockerEntity.setPort(0);
        dockerEntity.setStatus(0);
        if (parentId != null) {
            Files.copy(Path.of(dockerStoragePath + parentId), Path.of(dockerStoragePath + dockerId));
        }
        dockerRepository.save(dockerEntity);
        return dockerId;
    }

    public DockerEntity mountDocker(String dockerId, String password) throws IOException {
        DockerEntity dockerEntity = dockerRepository.findById(dockerId).get();
        String filePath = dockerStoragePath + dockerId;
        String mountPath = "/home/coder/project";
        int port = new Random().nextInt(10000) + 10000;
        ProcessBuilder builder = new ProcessBuilder("timeout 1800" +
                " docker run --rm -it" +
                " --name " + dockerId +
                " --env PASSWORD=" + password +
                " -p 0.0.0.0:" + port + ":8080" +
                " -v " + filePath + ":" + mountPath +
                " codercom/code-server");
        builder.redirectErrorStream(true);
        Process process = builder.start();
        dockerEntity.setPassword(password);
        dockerEntity.setPort(port);
        dockerEntity.setStatus(1);
        dockerRepository.save(dockerEntity);
        return dockerEntity;
    }

    public boolean umountDocker(String dockerId) {
        return true;
    }

    public String getUri(String dockerId, String baseUri) {
        DockerEntity dockerEntity = dockerRepository.findById(dockerId).get();
        return "http://" + baseUri.split(":")[0] + ":" + dockerEntity.getPort() + "?folder=/home/coder/project";
    }
}
