package com.codepass.user.service;

import com.codepass.user.dao.DockerRepository;
import com.codepass.user.dao.entity.DockerEntity;
import com.codepass.user.dao.entity.QuestionEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.PosixFileAttributes;
import java.nio.file.attribute.PosixFilePermission;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@Transactional
public class DockerService {
    @Value("${docker.storage.path}")
    private String dockerStoragePath;
    @Autowired
    DockerRepository dockerRepository;

    private static final Logger logger = LoggerFactory.getLogger(DockerService.class);

    private void setPerm(Path p) throws IOException {
        Set<PosixFilePermission> perms = Files.readAttributes(p, PosixFileAttributes.class).permissions();
        perms.add(PosixFilePermission.OWNER_WRITE);
        perms.add(PosixFilePermission.OWNER_READ);
        perms.add(PosixFilePermission.OWNER_EXECUTE);
        perms.add(PosixFilePermission.GROUP_WRITE);
        perms.add(PosixFilePermission.GROUP_READ);
        perms.add(PosixFilePermission.GROUP_EXECUTE);
        perms.add(PosixFilePermission.OTHERS_WRITE);
        perms.add(PosixFilePermission.OTHERS_READ);
        perms.add(PosixFilePermission.OTHERS_EXECUTE);
        Files.setPosixFilePermissions(p, perms);
    }

    public String createDocker(String parentId) throws IOException {
        String dockerId = UUID.randomUUID().toString().replace("-", "").substring(0, 8);
        DockerEntity dockerEntity = new DockerEntity();
        dockerEntity.setId(dockerId);
        dockerEntity.setPort(0);
        dockerEntity.setStatus(0);
        Path p;
        if (parentId != null) {
            new ProcessBuilder("/bin/sh", "-c",
                    "cp -r " + dockerStoragePath + parentId +
                            " " + dockerStoragePath + dockerId).start();
            // p = Files.copy(Path.of(dockerStoragePath + parentId), Path.of(dockerStoragePath + dockerId));
        } else {
            p = Files.createDirectory(
                    Path.of(dockerStoragePath + dockerId)
                    // , PosixFilePermissions.asFileAttribute(PosixFilePermissions.fromString("rwxrwxrwx"))
            );
        }

        // code-server里面的coder用户的id是1000:1000
        // docker -v时不能修改文件owner
        // 这里让外部和里面保持一致
        new ProcessBuilder("/bin/sh", "-c", "chown -R 1000:1000 " + dockerStoragePath + dockerId).start();
        // setPerm(p);
        dockerRepository.save(dockerEntity);
        return dockerId;
    }

    public DockerEntity mountDocker(String dockerId, String password) throws IOException {
        DockerEntity dockerEntity = dockerRepository.findById(dockerId).get();
        String filePath = dockerStoragePath + dockerId;
        String mountPath = "/home/coder/project";
        int port = new Random().nextInt(10000) + 10000;
        // timeout 1800 表示如果1800s内后面的命令还未启动就停止执行
        // 不能加-it参数否则会出问题
        ProcessBuilder builder = new ProcessBuilder("/bin/sh", "-c",
                "timeout 1800" +
                        " docker run --rm" +
                        " --name " + dockerId +
                        " --env PASSWORD=" + password +
                        " -p 0.0.0.0:" + port + ":8080" +
                        " -v " + filePath + ":" + mountPath +
                        " codercom/code-server");
        builder.redirectErrorStream(true);
        builder.start();
        dockerEntity.setPassword(password);
        dockerEntity.setPort(port);
        dockerEntity.setStatus(1);
        dockerRepository.save(dockerEntity);
        return dockerEntity;
    }

    public DockerEntity umountDocker(String dockerId) throws IOException {
        DockerEntity dockerEntity = dockerRepository.findById(dockerId).get();
        ProcessBuilder builder = new ProcessBuilder("/bin/sh", "-c", "docker stop " + dockerId);
        builder.redirectErrorStream(true);
        builder.start();
        dockerEntity.setPort(0);
        dockerEntity.setStatus(0);
        dockerRepository.save(dockerEntity);
        return dockerEntity;
    }

    public String getUri(String dockerId, String baseUri) {
        DockerEntity dockerEntity = dockerRepository.findById(dockerId).get();
        return "http://" + baseUri.split(":")[0] + ":" + dockerEntity.getPort() + "?folder=/home/coder/project";
    }

    public String getDiff(String parentId, String dockerId) throws IOException {
        Process process = new ProcessBuilder("/bin/sh", "-c",
                "diff -ruNa " + dockerStoragePath + parentId + " " + dockerStoragePath + dockerId).start();
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8));
        return reader.lines().collect(Collectors.joining("\n"))
                .replace(dockerStoragePath + parentId, "/origin")
                .replace(dockerStoragePath + dockerId, "/new");
    }

    public Page<DockerEntity> getAllDocker(int page) {
        return dockerRepository.findAll(PageRequest.of(page, 10));
    }

    public void deleteDocker(String dockerId) {
        dockerRepository.deleteById(dockerId);
    }

    public DockerEntity getDockerById(String dockerId) {
        return dockerRepository.findById(dockerId).get();
    }
}
