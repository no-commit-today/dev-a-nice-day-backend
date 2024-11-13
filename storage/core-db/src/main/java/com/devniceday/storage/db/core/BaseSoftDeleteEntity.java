package com.devniceday.storage.db.core;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class BaseSoftDeleteEntity extends BaseTimeEntity {

    @Column(name = "deleted", nullable = false)
    private Boolean deleted = false;

    protected BaseSoftDeleteEntity() {
    }

    public boolean isDeleted() {
        return Boolean.TRUE.equals(deleted);
    }

    public boolean isUsed() {
        return Boolean.FALSE.equals(deleted);
    }

    public void delete() {
        this.deleted = true;
    }

    public void restore() {
        this.deleted = false;
    }
}
