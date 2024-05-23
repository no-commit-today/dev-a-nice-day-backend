package com.nocommittoday.techswipe.core.infrastructure.mysql;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;

import java.time.LocalDateTime;

@MappedSuperclass
public abstract class BaseSoftDeleteEntity {

    @Column(name = "deleted", nullable = false)
    private Boolean deleted = false;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    protected BaseSoftDeleteEntity() {
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    public boolean isDeleted() {
        return Boolean.TRUE.equals(deleted);
    }

    public void delete() {
        this.deleted = true;
        this.deletedAt = LocalDateTime.now();
    }
}
