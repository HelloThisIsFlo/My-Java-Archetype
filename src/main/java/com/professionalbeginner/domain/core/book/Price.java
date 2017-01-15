package com.professionalbeginner.domain.core.book;

import com.google.common.base.MoreObjects;
import com.professionalbeginner._other.ddd.ValueObject;

import java.util.Objects;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * @author Kempenich Florian
 */
public class Price implements ValueObject<Price> {

    private double amount;

    public Price(double amount) {
        checkArgument(amount >= 0, "Amount cannot be < 0");
        this.amount = amount;
    }

    @Override
    public boolean sameValueAs(Price other) {
        return Double.compare(other.amount, amount) == 0;
//        return other.amount == amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Price price = (Price) o;
        return sameValueAs(price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount);
    }

    public double amount() {
        return amount;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("amount", amount)
                .toString();
    }
}
