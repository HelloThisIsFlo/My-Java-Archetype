package com.professionalbeginner.domain.core.user;

import com.professionalbeginner._other.ddd.Entity;

import java.util.Objects;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Kempenich Florian
 */
public class User implements Entity<User> {

    private UserId userId;

    public User(UserId userId) {
        this.userId = checkNotNull(userId);
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
