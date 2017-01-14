package com.professionalbeginner.domain.core.review;

import com.professionalbeginner.domain.ddd.ValueObject;

import java.util.Objects;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Kempenich Florian
 */
public class User implements ValueObject<User> {
    private String username;

    public User(String username) {
        checkNotNull(username);
        checkArgument(!username.isEmpty(), "Username shouldn't be empty");
        this.username = username;
    }

    public String username() {
        return username;
    }

    @Override
    public boolean sameValueAs(User other) {
        return other.username().equals(username);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return sameValueAs(user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }
}
