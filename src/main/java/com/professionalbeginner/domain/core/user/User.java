package com.professionalbeginner.domain.core.user;

import com.professionalbeginner._other.ddd.Entity;

import java.util.Objects;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Kempenich Florian
 */
public class User implements Entity<User> {

    private UserId userId;
    private UserInfo info;

    public User(UserId userId, UserInfo userInfo) {
        setUserId(userId);
        setInfo(userInfo);
    }

    public UserId getUserId() {
        return userId;
    }

    public void setUserId(UserId userId) {
        this.userId = checkNotNull(userId);
    }

    public UserInfo getInfo() {
        return info;
    }

    public void setInfo(UserInfo info) {
        this.info = checkNotNull(info);
    }

    @Override
    public boolean sameIdentityAs(User other) {
        return Objects.equals(other.userId, this.userId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return sameIdentityAs(user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }
}
