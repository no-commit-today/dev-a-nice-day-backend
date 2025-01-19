package com.devniceday.storage.db.core;

import com.devniceday.batch.domain.SubscriptionType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import javax.annotation.Nullable;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(
        name = "subscription",
        indexes = {
                @Index(name = "ix_providerid", columnList = "provider_id")
        }
)
public class BatchSubscriptionEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "provider_id", updatable = false, nullable = false)
    private long providerId;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", columnDefinition = "varchar(45)", nullable = false)
    private SubscriptionType type;

    @Nullable
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "feed", columnDefinition = "json")
    private BatchFeedSubscriptionData feed;

    @Nullable
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "list_scrapping", columnDefinition = "json")
    private BatchWebListSubscriptionData webList;

    @Column(name = "disabled", nullable = false)
    private boolean disabled = false;

    protected BatchSubscriptionEntity() {
    }

    public Long getId() {
        return id;
    }

    public long getProviderId() {
        return providerId;
    }

    public SubscriptionType getType() {
        return type;
    }

    @Nullable
    public BatchFeedSubscriptionData getFeed() {
        return feed;
    }

    @Nullable
    public BatchWebListSubscriptionData getWebList() {
        return webList;
    }

    public void disable() {
        this.disabled = true;
    }

    public boolean isDisabled() {
        return disabled;
    }
}
