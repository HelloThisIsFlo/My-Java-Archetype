package com.professionalbeginner.domain.core.user;

import com.professionalbeginner._other.ddd.ValueObject;

import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Kempenich Florian
 */
public class UserInfo implements ValueObject<UserInfo> {

    public final String firstName;
    public final String lastName;
    public final LocalDate birthDate;

    public UserInfo(String firstName, String lastName, LocalDate birthDate) {
        checkArgument(tooFarPast(birthDate), "Date too far in the past");
        checkArgument(inFuture(birthDate), "Date in future");
        this.firstName = checkNotNull(firstName);
        this.lastName = checkNotNull(lastName);
        this.birthDate = checkNotNull(birthDate);
    }

    private boolean tooFarPast(LocalDate birthDate) {
        LocalDate limitPast = LocalDate.now().minusYears(150);
        return birthDate.compareTo(limitPast) > 0;
    }

    private boolean inFuture(LocalDate birthDate) {
        return birthDate.compareTo(LocalDate.now()) < 0;
    }

    @Override
    public boolean sameValueAs(UserInfo other) {
        return Objects.equals(firstName, other.firstName) &&
                Objects.equals(lastName, other.lastName) &&
                Objects.equals(birthDate, other.birthDate);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserInfo userInfo = (UserInfo) o;
        return sameValueAs(userInfo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, birthDate);
    }
}
