package com.professionalbeginner._other.ddd;

/**
 * @author Kempenich Florian
 */
public interface Entity<T> {

    /**
     * Entities compare by identity, not by attributes.
     *
     * @param other The other entity.
     * @return true if the identities are the same, regardless of other attributes.
     */
    boolean sameIdentityAs(T other);
}
