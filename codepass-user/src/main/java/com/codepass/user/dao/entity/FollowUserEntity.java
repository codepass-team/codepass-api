package com.codepass.user.dao.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "FollowUser", schema = "codepass", catalog = "")
@IdClass(FollowUserEntityPK.class)
public class FollowUserEntity {
    private int followUser;
    private int beFollowedUser;
    private Timestamp followTime;

    @Id
    @Column(name = "followUser")
    public int getFollowUser() {
        return followUser;
    }

    public void setFollowUser(int followUser) {
        this.followUser = followUser;
    }

    @Id
    @Column(name = "beFollowedUser")
    public int getBeFollowedUser() {
        return beFollowedUser;
    }

    public void setBeFollowedUser(int beFollowedUser) {
        this.beFollowedUser = beFollowedUser;
    }

    @Basic
    @Column(name = "followTime")
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
