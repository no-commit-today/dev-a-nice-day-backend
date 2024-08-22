package com.nocommittoday.techswipe.storage.mysql.subscription;

import com.nocommittoday.techswipe.domain.content.TechContentProviderId;
import com.nocommittoday.techswipe.domain.subscription.Subscription;
import com.nocommittoday.techswipe.domain.subscription.SubscriptionId;
import com.nocommittoday.techswipe.domain.subscription.SubscriptionType;
import com.nocommittoday.techswipe.storage.mysql.content.TechContentProviderEntity;
import com.nocommittoday.techswipe.storage.mysql.core.BaseSoftDeleteEntity;
import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Optional;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(
        name = "subscription",
        indexes = {
                @Index(name = "ix_providerid", columnList = "provider_id")
        }
)
public class SubscriptionEntity extends BaseSoftDeleteEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @OneToOne(fetch = LAZY, optional = false)
    @JoinColumn(name = "provider_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private TechContentProviderEntity provider;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", columnDefinition = "varchar(45)", nullable = false)
    private SubscriptionType type;

    @Nullable
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "feed", columnDefinition = "json")
    private FeedSubscriptionData feed;

    @Nullable
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "list_scrapping", columnDefinition = "json")
    private ListScrappingSubscriptionData listScrapping;

    protected SubscriptionEntity() {
    }

    public SubscriptionEntity(
            TechContentProviderEntity provider,
            SubscriptionType type,
            @Nullable FeedSubscriptionData feed,
            @Nullable ListScrappingSubscriptionData listScrapping
    ) {
        this.provider = provider;
        this.type = type;
        this.feed = feed;
        this.listScrapping = listScrapping;
    }

    public Subscription toDomain() {
        return switch (type) {
            case FEED -> Subscription.createFeed(
                    new SubscriptionId(id),
                    Optional.ofNullable(provider)
                            .map(TechContentProviderEntity::getId)
                            .map(TechContentProviderId::new).orElseThrow(),
                    Objects.requireNonNull(feed).getUrl(),
                    feed.getContentScrapping().toDomain()
            );
            case LIST_SCRAPPING -> Subscription.createListScrapping(
                    new SubscriptionId(id),
                    Optional.ofNullable(provider)
                            .map(TechContentProviderEntity::getId)
                            .map(TechContentProviderId::new).orElseThrow(),
                    Objects.requireNonNull(listScrapping).getListScrapping().toDomain(),
                    listScrapping.getContentScrapping().toDomain()
            );
        };
    }

    public Long getId() {
        return id;
    }

    public TechContentProviderEntity getProvider() {
        return provider;
    }

    public SubscriptionType getType() {
        return type;
    }

    @Nullable
    public FeedSubscriptionData getFeed() {
        return feed;
    }

    @Nullable
    public ListScrappingSubscriptionData getListScrapping() {
        return listScrapping;
    }
}
