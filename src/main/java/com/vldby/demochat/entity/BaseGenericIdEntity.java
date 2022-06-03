package com.vldby.demochat.entity;

import javax.persistence.MappedSuperclass;
import java.util.Objects;

@MappedSuperclass
public abstract class BaseGenericIdEntity<T> {
    public abstract T getId();
    public abstract void setId(T id);

    @Override
    public boolean equals(Object other) {
        if (this == other)
            return true;

        if (other == null || getClass() != other.getClass())
            return false;

        return Objects.equals(getId(), ((BaseGenericIdEntity) other).getId());
    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }

    @Override
    public String toString() {
        return getClass().getName() + "-" + getId();
    }
}
