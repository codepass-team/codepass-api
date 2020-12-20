package com.codepass.user.dao.entity;

import javax.persistence.*;
import java.sql.Timestamp;

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

    public void setFollowUser(int followUser) {
        this.followUser = followUser;
    }

    @Id
    @Column(name = "be_followed_user")
    public int getBeFollowedUser() {
        return beFollowedUser;
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

        if (followUser != that.followUser) return false;
        if (beFollowedUser != that.beFollowedUser) return false;
        if (followTime != null ? !followTime.equals(that.followTime) : that.followTime != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = followUser;
        result = 31 * result + beFollowedUser;
        result = 31 * result + (followTime != null ? followTime.hashCode() : 0);
        return result;
    }
}
