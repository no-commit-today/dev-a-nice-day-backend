package com.nocommittoday.techswipe.domain.rds.core;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

@MappedSuperclass
public abstract class BaseEntity extends BaseSoftDeleteEntity {

    @Version
    @Column(name = "version")
    private Integer version;

    @CreatedBy
    @Column(name = "created_by", updatable = false, nullable = false)
    private String createdBy;

    @LastModifiedBy
    @Column(name = "last_modified_by", nullable = false)
    private String lastModifiedBy;

    protected BaseEntity() {
    }

    public Integer getVersion() {
        return version;
    }
}
