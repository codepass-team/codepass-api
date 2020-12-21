package com.codepass.user.controller;

import com.codepass.user.dao.DockerRepository;
import com.codepass.user.service.DockerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@Tag(name = "Misc", description = "杂项API")
public class MiscController {
    @Autowired
    DockerRepository dockerRepository;
    @Autowired
    DockerService dockerService;

    @GetMapping("/api/docker/{dockerId}")
    @Operation(summary = "进入容器", description = "进入容器")
    public ResponseEntity<?> redirectToDocker(@Parameter(description = "容器Id") @PathVariable String dockerId,
                                              HttpServletRequest request) {
        String baseUri = request.getHeader("Host");
        return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY).header(HttpHeaders.LOCATION, dockerService.getUri(dockerId, baseUri)).build();
    }
}
