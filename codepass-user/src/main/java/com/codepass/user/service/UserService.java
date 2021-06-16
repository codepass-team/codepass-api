package com.codepass.user.service;

import com.codepass.user.dao.FollowUserRepository;
import com.codepass.user.dao.UserRepository;
import com.codepass.user.dao.entity.FollowUserEntity;
import com.codepass.user.dao.entity.UserEntity;
import com.sun.mail.util.MailSSLSocketFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

import java.sql.Timestamp;
import java.util.Properties;
import java.util.Random;

@Component
@Transactional
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    FollowUserRepository followUserRepository;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    public UserEntity updateUser(int userId, String username, String gender, String job, String tech, Integer age, String email) {
        UserEntity userEntity = userRepository.findById(userId).orElseGet(UserEntity::new);
        if (username != null) userEntity.setUsername(username);
        if (gender != null) userEntity.setGender(gender);
        if (job != null) userEntity.setJob(job);
        if (tech != null) userEntity.setTech(tech);
        if (age != null) userEntity.setAge(age);
        if (email != null) userEntity.setEmail(email);
        userRepository.save(userEntity);
        return userEntity;
    }

    public UserEntity getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public UserEntity getUserById(int id) {
        return userRepository.findById(id).get();
    }

    public UserEntity createNewUser(String username, String password) {
        UserEntity newUser = new UserEntity();
        newUser.setUsername(username);
        newUser.setPassword(bcryptEncoder.encode(password));
        userRepository.save(newUser);
        return newUser;
    }

    public void followUser(int followerId, int beFollowerId) {
        if (followerId == beFollowerId) {
            throw new RuntimeException("Cannot follow self");
        }
        FollowUserEntity followUserEntity = new FollowUserEntity();
        followUserEntity.setFollowUser(followerId);
        followUserEntity.setBeFollowedUser(beFollowerId);
        followUserEntity.setFollowTime(new Timestamp(System.currentTimeMillis()));
        followUserRepository.save(followUserEntity);
    }

    public void notFollowUser(int followerId, int beFollowerId) {
        FollowUserEntity followUserEntity = new FollowUserEntity();
        followUserEntity.setFollowUser(followerId);
        followUserEntity.setBeFollowedUser(beFollowerId);
        followUserRepository.delete(followUserEntity);
    }

    public Page<UserEntity> getAllUser(int page) {
        return userRepository.findAll(PageRequest.of(page, 10));
    }

    public void deleteUser(Integer userId) {
        userRepository.deleteById(userId);
    }

    public String sendEmail(String name) {
        UserEntity userEntity = userRepository.findByUsername(name);
        if (userEntity == null || userEntity.getEmail() == null || userEntity.getEmail().equals(""))
            return "未找到此用户或此用户未设置邮箱，请联系管理员处理。";
        try {
            // 收件人电子邮箱
            String to = userEntity.getEmail();
            // 发件人电子邮箱
            String from = "2277861660@qq.com";
            // 指定发送邮件的主机为 smtp.qq.com
            String host = "smtp.qq.com";  //QQ 邮件服务器
            // 获取系统属性
            Properties properties = System.getProperties();
            // 设置邮件服务器
            properties.setProperty("mail.smtp.host", host);
            properties.put("mail.smtp.auth", "true");

            //阿里云服务器禁用25端口，所以服务器上改为465端口
	        properties.put("mail.smtp.socketFactory.port", "465");
	        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
	        properties.setProperty("mail.smtp.socketFactory.fallback", "false");
	        properties.setProperty("mail.smtp.socketFactory.port", "465");

//            MailSSLSocketFactory sf = new MailSSLSocketFactory();
//            sf.setTrustAllHosts(true);
//            properties.put("mail.smtp.ssl.enable", "true");
//            properties.put("mail.smtp.ssl.socketFactory", sf);
            // 获取默认session对象
            Session session = Session.getDefaultInstance(properties, new Authenticator() {
                public PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(from, "vlokfadsbaxbecfg"); //发件人邮件用户名、授权码
                }
            });
            // 创建默认的 MimeMessage 对象
            MimeMessage message = new MimeMessage(session);
            // Set From: 头部头字段
            message.setFrom(new InternetAddress(from));
            // Set To: 头部头字段
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            // Set Subject: 头部头字段
            message.setSubject("CodePass - 密码重置验证码");

            userEntity.setCaptcha(new Random().nextInt(900000) + 100000);
            userEntity.setRaiseTime(new Timestamp(System.currentTimeMillis() + 10 * 60 * 1000));
            userRepository.save(userEntity);

            // 设置消息体
            message.setText("您的验证码是 " + userEntity.getCaptcha().toString() + " ，十分钟内有效，请尽快输入。若验证码无效则请重新申请发送邮件。");
            // 发送消息
            Transport.send(message);
            System.out.println("Sent message successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            return "邮件发送失败，请稍后重试或联系管理员。";
        }
        return "邮件发送成功，请至您设置的邮箱查看。";
    }

    public String resetPassword(String name, String password, int captcha) {
        UserEntity userEntity = userRepository.findByUsername(name);
        if (userEntity == null)
            return "未找到此用户名。";
        if (userEntity.getCaptcha() == captcha && System.currentTimeMillis() < userEntity.getRaiseTime().getTime()) {
            userEntity.setPassword(password);
            userEntity.setRaiseTime(new Timestamp(System.currentTimeMillis()));
            userRepository.save(userEntity);
            return "密码重置成功！";
        } else {
            return "验证码不正确或已过期，请重新输入。";
        }
    }
}
