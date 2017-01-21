package com.professionalbeginner.domain.core.user;

import com.professionalbeginner._other.ddd.Entity;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Kempenich Florian
 */
public class User implements Entity<User> {

    public static final User NULL = new User(UserId.NULL, UserInfo.NULL);
    private static final int LEGAL_AGE = 18;

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
        checkIfInLegalAge(info);
        this.info = checkNotNull(info);
    }

    private void checkIfInLegalAge(UserInfo info) {
        if (notInLegalAge(info.birthdate)) {
            throw new IllegalUserException("User not in legal age: " + info, info);
        }
    }

    private boolean notInLegalAge(LocalDate birthDate) {
        LocalDate now = LocalDate.now();
        long userAge = birthDate.until(now, ChronoUnit.YEARS);
        return userAge < LEGAL_AGE;
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
