package com.codepass.user.controller;

import com.codepass.user.dao.UserRepository;
import com.codepass.user.dao.entity.UserEntity;
import com.codepass.user.dto.PageChunk;
import com.codepass.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/api/user")
@Tag(name = "User", description = "用户管理相关API")
//@Secured()
public class UserController {

    final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @PostMapping
    @Operation(summary = "修改用户资料", description = "修改用户资料")
    public ResponseEntity<?> updateUserData(@Parameter(description = "用户昵称") @RequestParam(required = false) String username,
                                            @Parameter(description = "用户性别") @RequestParam(required = false) String gender,
                                            @Parameter(description = "用户工作") @RequestParam(required = false) String job,
                                            @Parameter(description = "用户职业") @RequestParam(required = false) String tech,
                                            @Parameter(description = "用户年龄") @RequestParam(required = false) Integer age,
                                            @Parameter(description = "用户邮箱") @RequestParam(required = false) String email) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserEntity userEntity = userRepository.findByUsername(userDetails.getUsername());
        return ResponseEntity.ok(new HashMap<String, Object>() {{
            put("status", "ok");
            put("data", userService.updateUser(userEntity.getId(), username, gender, job, tech, age, email));
        }});
    }

    @GetMapping
    @Operation(summary = "获取用户资料", description = "获取用户资料")
    public ResponseEntity<?> getUserData() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserEntity userEntity = userRepository.findByUsername(userDetails.getUsername());
        return ResponseEntity.ok(new HashMap<String, Object>() {{
            put("status", "ok");
            put("data", userService.getUserById(userEntity.getId()));
        }});
    }

    @PostMapping("/follow/{userId}")
    @Operation(summary = "关注用户", description = "关注用户")
    public ResponseEntity<?> followUser(@PathVariable int userId) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserEntity userEntity = userRepository.findByUsername(userDetails.getUsername());
        userService.followUser(userEntity.getId(), userId);
        return ResponseEntity.ok("Ok");
    }


    @DeleteMapping("/follow/{userId}")
    @Operation(summary = "取消关注用户", description = "取消关注用户")
    public ResponseEntity<?> notFollowUser(@PathVariable int userId) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserEntity userEntity = userRepository.findByUsername(userDetails.getUsername());
        userService.notFollowUser(userEntity.getId(), userId);
        return ResponseEntity.ok("Ok");
    }

    @GetMapping("/listAll")
    @Operation(summary = "获取数据库里的所有用户", description = "获取数据库里的所有用户", tags = "Admin")
    public ResponseEntity<?> listAllUsers(@Parameter(description = "页码, 从0开始") @RequestParam(defaultValue = "0") int page) throws RuntimeException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN")))
            return ResponseEntity.status(401).build();
        Page<UserEntity> userEntities = userService.getAllUser(page);
        PageChunk users = new PageChunk(userEntities);
        return ResponseEntity.ok(new HashMap<String, Object>() {{
            put("status", "ok");
            put("data", users);
        }});
    }

    @DeleteMapping("/{userId}")
    @Operation(summary = "删除用户", description = "删除一个用户", tags = "Admin")
    public ResponseEntity<?> deleteUser(@Parameter(description = "用户Id") @PathVariable int userId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN")))
            return ResponseEntity.status(401).build();
        userService.deleteUser(userId);
        return ResponseEntity.ok(new HashMap<String, Object>() {{
            put("status", "ok");
        }});
    }
}
