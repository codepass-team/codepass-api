package com.codepass.user.dao.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

public class FollowUserEntityPK implements Serializable {
    private int followUser;
    private int beFollowedUser;

    @Column(name = "follow_user")
    @Id
    public int getFollowUser() {
        return followUser;
    }

    public void setFollowUser(int followUser) {
        this.followUser = followUser;
    }

    @Column(name = "be_followed_user")
    @Id
    public int getBeFollowedUser() {
        return beFollowedUser;
    }

    public void setBeFollowedUser(int beFollowedUser) {
        this.beFollowedUser = beFollowedUser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FollowUserEntityPK that = (FollowUserEntityPK) o;

        if (followUser != that.followUser) return false;
        if (beFollowedUser != that.beFollowedUser) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = followUser;
        result = 31 * result + beFollowedUser;
        return result;
    }
}
