package com.professionalbeginner.domain.core.user;

import com.professionalbeginner._other.ddd.ValueObject;

import java.util.Date;
import java.util.Objects;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Kempenich Florian
 */
public class UserInfos implements ValueObject<UserInfos> {

    public final String firstName;
    public final String lastName;
    public final Date birthDate;

    public UserInfos(String firstName, String lastName, Date birthDate) {
        this.firstName = checkNotNull(firstName);
        this.lastName = checkNotNull(lastName);
        this.birthDate = checkNotNull(birthDate);
    }

    @Override
    public boolean sameValueAs(UserInfos other) {
        return Objects.equals(firstName, other.firstName) &&
                Objects.equals(lastName, other.lastName) &&
                Objects.equals(birthDate, other.birthDate);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserInfos userInfos = (UserInfos) o;
        return sameValueAs(userInfos);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, birthDate);
    }
}
