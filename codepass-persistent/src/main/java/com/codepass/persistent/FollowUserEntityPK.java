package com.codepass.persistent;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class FollowUserEntityPK implements Serializable {
    private int followUser;
    private int beFollowedUser;

    @Column(name = "followUser")
    @Id
    public int getFollowUser() {
        return followUser;
    }

    public void setFollowUser(int followUser) {
        this.followUser = followUser;
    }

    @Column(name = "beFollowedUser")
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
        return followUser == that.followUser && beFollowedUser == that.beFollowedUser;
    }

    @Override
    public int hashCode() {
        return Objects.hash(followUser, beFollowedUser);
    }
}
