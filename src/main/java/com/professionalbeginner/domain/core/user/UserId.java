package com.professionalbeginner.domain.core.user;

import com.google.common.base.MoreObjects;
import com.professionalbeginner._other.ddd.ValueObject;

import java.util.Objects;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Kempenich Florian
 */
public class UserId implements ValueObject<UserId> {

    public final static UserId NULL = new UserId();

    private String username;

    private UserId() {
        this.username = "";
    }
    public UserId(String username) {
        checkNotNull(username);
        checkArgument(!username.isEmpty(), "Username shouldn't be empty");
        this.username = username;
    }

    public String username() {
        return username;
    }

    @Override
    public boolean sameValueAs(UserId other) {
        return other.username().equals(username);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserId userId = (UserId) o;
        return sameValueAs(userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("username", username)
                .toString();
    }
}
