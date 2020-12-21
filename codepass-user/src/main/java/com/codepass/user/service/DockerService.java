package com.codepass.user.service;

import com.codepass.user.dao.DockerRepository;
import com.codepass.user.dao.entity.DockerEntity;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    DockerRepository dockerRepository;

    public String createDocker() throws IOException {
        String dockerId = UUID.randomUUID().toString().replace("-", "").substring(0, 8);
        Files.createDirectory(
                Path.of(dockerStoragePath + dockerId),
                PosixFilePermissions.asFileAttribute(PosixFilePermissions.fromString("rwxrwxrwx"))
        );
        DockerEntity dockerEntity = new DockerEntity();
        dockerEntity.setId(dockerId);
        dockerEntity.setPort(0);
        dockerEntity.setStatus(0);
        dockerRepository.save(dockerEntity);
        return dockerId;
    }

    public String cloneDocker(String dockerId) throws IOException {
        String newDockerId = createDocker();
        Files.copy(Path.of(dockerStoragePath + dockerId), Path.of(dockerStoragePath + newDockerId));
        return newDockerId;
    }

    public DockerEntity mountDocker(String dockerId, String password) throws IOException {
        DockerEntity dockerEntity = dockerRepository.findById(dockerId).get();
        String filePath = dockerStoragePath + dockerId;
        String mountPath = "/home/coder/project";
        int port = new Random().nextInt(10000) + 10000;
        Process process = Runtime.getRuntime().exec("timeout 1800" +
                " docker run --rm -it" +
                " --name " + dockerId +
                " --env PASSWORD=" + password +
                " -p 0.0.0.0:" + port + ":8080" +
                " -v " + filePath + ":" + mountPath +
                " codercom/code-server");
        dockerEntity.setPassword(password);
        dockerEntity.setStatus(1);
        dockerRepository.save(dockerEntity);
        return dockerEntity;
    }

    public boolean umountDocker(String dockerId) {
        return true;
    }

    public String getUri(String dockerId, String baseUri) {
        DockerEntity dockerEntity = dockerRepository.findById(dockerId).get();
        return baseUri.split(":/")[0] + ":" + dockerEntity.getPort() + "/?folder=/home/coder/project";
    }
}
