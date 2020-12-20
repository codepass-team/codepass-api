package com.codepass.user.dao.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "follow_user", schema = "codepass", catalog = "")
@IdClass(FollowUserEntityPK.class)
public class FollowUserEntity {
    private int followUser;
    private int beFollowedUser;
    private Timestamp followTime;

    @Id
    @Column(name = "follow_user")
    public int getFollowUser() {
        return followUser;
    }

    public void setFollowUser(Integer followUser) {
        this.followUser = followUser;
    }

    public void setFollowUser(int followUser) {
        this.followUser = followUser;
    }

    @Id
    @Column(name = "be_followed_user")
    public int getBeFollowedUser() {
        return beFollowedUser;
    }

    public void setBeFollowedUser(Integer beFollowedUser) {
        this.beFollowedUser = beFollowedUser;
    }

    public void setBeFollowedUser(int beFollowedUser) {
        this.beFollowedUser = beFollowedUser;
    }

    @Basic
    @Column(name = "follow_time")
    public Timestamp getFollowTime() {
        return followTime;
    }

    public void setFollowTime(Timestamp followTime) {
        this.followTime = followTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FollowUserEntity that = (FollowUserEntity) o;
        return followUser == that.followUser && beFollowedUser == that.beFollowedUser && Objects.equals(followTime, that.followTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(followUser, beFollowedUser, followTime);
    }
}
