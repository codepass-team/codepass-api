package com.codepass.user.dao.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "user", schema = "codepass", catalog = "")
public class UserEntity {
    private int id;
    private String email;
    private String password;
    private String nickname;
    private Integer gender;
    private String job;
    private String tech;
    private Integer age;
    private Integer followerCount;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "nickname")
    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Basic
    @Column(name = "gender")
    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    @Basic
    @Column(name = "job")
    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    @Basic
    @Column(name = "tech")
    public String getTech() {
        return tech;
    }

    public void setTech(String tech) {
        this.tech = tech;
    }

    @Basic
    @Column(name = "age")
    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Basic
    @Column(name = "follower_count")
    public Integer getFollowerCount() {
        return followerCount;
    }

    public void setFollowerCount(Integer followerCount) {
        this.followerCount = followerCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity that = (UserEntity) o;
        return id == that.id && Objects.equals(email, that.email) && Objects.equals(password, that.password) && Objects.equals(nickname, that.nickname) && Objects.equals(gender, that.gender) && Objects.equals(job, that.job) && Objects.equals(tech, that.tech) && Objects.equals(age, that.age) && Objects.equals(followerCount, that.followerCount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, password, nickname, gender, job, tech, age, followerCount);
    }
}
