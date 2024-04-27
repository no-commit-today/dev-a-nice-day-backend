package com.nocommittoday.techswipe.domain.rds.core;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;

import java.time.LocalDateTime;

@MappedSuperclass
public class BaseSoftDeleteEntity extends BaseTimeEntity {

    @Column(name = "deleted", nullable = false)
    private Boolean deleted = false;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    protected BaseSoftDeleteEntity() {
    }

    public boolean isDeleted() {
        return Boolean.TRUE.equals(deleted);
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    public void delete() {
        this.deleted = true;
        this.deletedAt = LocalDateTime.now();
    }
}
