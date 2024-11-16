package com.devniceday.storage.db.core;

import com.devniceday.core.enums.TechContentProviderType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import org.springframework.data.domain.Persistable;

@Entity
@Table(
        name = "tech_content_provider",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_techcontentprovider__url", columnNames = {"url"})
        }
)
public class TechContentProviderEntity extends BaseEntity implements Persistable<Long> {

    @Id
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", columnDefinition = "varchar(45)", nullable = false)
    private TechContentProviderType type;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "url", length = 500, nullable = false)
    private String url;

    protected TechContentProviderEntity() {
    }

    @Override
    public boolean isNew() {
        return getCreatedAt() == null;
    }

    @Override
    public Long getId() {
        return id;
    }

    public TechContentProviderType getType() {
        return type;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }
}
