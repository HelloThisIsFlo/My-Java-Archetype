package com.professionalbeginner.domain.core.book;

import com.google.common.base.MoreObjects;
import com.professionalbeginner._other.ddd.ValueObject;

import java.util.Objects;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * @author Kempenich Florian
 */
public class BookId implements ValueObject<BookId> {

    private final Long id;

    public static final BookId NOT_ASSIGNED = new BookId();
    private BookId() {
        this.id = -1L;
    }

    public BookId(Long id) {
        checkArgument(id > 0, "Id cannot be <= 0");
        this.id = id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookId bookId = (BookId) o;
        return sameValueAs(bookId);
    }

    @Override
    public boolean sameValueAs(BookId other) {
        return Objects.equals(id, other.id);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .toString();
    }

    public long idLong() {
        return id;
    }
}
