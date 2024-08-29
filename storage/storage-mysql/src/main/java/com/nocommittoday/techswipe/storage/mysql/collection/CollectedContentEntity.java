package com.nocommittoday.techswipe.storage.mysql.collection;

import com.nocommittoday.techswipe.domain.collection.CollectedContent;
import com.nocommittoday.techswipe.domain.collection.CollectedContentId;
import com.nocommittoday.techswipe.domain.collection.CollectionCategory;
import com.nocommittoday.techswipe.domain.collection.CollectionCategoryList;
import com.nocommittoday.techswipe.domain.collection.CollectionStatus;
import com.nocommittoday.techswipe.domain.content.Summary;
import com.nocommittoday.techswipe.domain.content.TechContentId;
import com.nocommittoday.techswipe.domain.content.TechContentProviderId;
import com.nocommittoday.techswipe.domain.subscription.SubscriptionId;
import com.nocommittoday.techswipe.storage.mysql.content.TechContentEntity;
import com.nocommittoday.techswipe.storage.mysql.content.TechContentProviderEntity;
import com.nocommittoday.techswipe.storage.mysql.core.BaseSoftDeleteEntity;
import com.nocommittoday.techswipe.storage.mysql.subscription.SubscriptionEntity;
import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import org.springframework.data.domain.Persistable;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Entity
@Table(
        name = "collected_content",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_collected_content__url", columnNames = {"url"})
        }
)
public class CollectedContentEntity extends BaseSoftDeleteEntity implements Persistable<Long> {

    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", columnDefinition = "varchar(45)", nullable = false)
    private CollectionStatus status;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "provider_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private TechContentProviderEntity provider;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "subscription_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private SubscriptionEntity subscription;

    @Nullable
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "published_content_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private TechContentEntity publishedContent;

    @Column(name = "url", length = 500, nullable = false)
    private String url;

    @Column(name = "title", length = 500, nullable = false)
    private String title;

    @Column(name = "published_date", nullable = false)
    private LocalDate publishedDate;

    @Lob
    @Column(name = "content", length = 100_000_000, nullable = false)
    private String content;

    @Nullable
    @Column(name = "image_url", length = 500)
    private String imageUrl;

    @Nullable
    @Convert(converter = CategoryListConverter.class)
    @Column(name = "categories", length = 500)
    private List<CollectionCategory> categories;

    @Nullable
    @Column(name = "summary", length = 2_000)
    private String summary;

    protected CollectedContentEntity() {
    }

    public CollectedContentEntity(
            Long id,
            CollectionStatus status,
            TechContentProviderEntity provider,
            SubscriptionEntity subscription,
            TechContentEntity publishedContent,
            String url,
            String title,
            LocalDate publishedDate,
            String content,
            @Nullable String imageUrl,
            @Nullable List<CollectionCategory> categories,
            @Nullable String summary
    ) {
        this.id = id;
        this.status = status;
        this.provider = provider;
        this.subscription = subscription;
        this.publishedContent = publishedContent;
        this.url = url;
        this.title = title;
        this.publishedDate = publishedDate;
        this.content = content;
        this.imageUrl = imageUrl;
        this.categories = categories;
        this.summary = summary;
    }

    public CollectedContent toDomain() {
        return new CollectedContent(
                new CollectedContentId(id),
                status,
                categories != null ? new CollectionCategoryList(categories) : null,
                new Summary(summary),
                Optional.ofNullable(provider)
                        .map(TechContentProviderEntity::getId)
                        .map(TechContentProviderId::new)
                        .orElseThrow(),
                Optional.ofNullable(subscription)
                        .map(SubscriptionEntity::getId)
                        .map(SubscriptionId::new)
                        .orElseThrow(),
                Optional.ofNullable(publishedContent)
                        .map(TechContentEntity::getId)
                        .map(TechContentId::new)
                        .orElse(null),
                url,
                title,
                publishedDate,
                content,
                imageUrl
        );
    }

    @Override
    public boolean isNew() {
        return getCreatedAt() == null;
    }

    @Override
    public Long getId() {
        return id;
    }

    public CollectionStatus getStatus() {
        return status;
    }

    public TechContentProviderEntity getProvider() {
        return provider;
    }

    public SubscriptionEntity getSubscription() {
        return subscription;
    }

    @Nullable
    public TechContentEntity getPublishedContent() {
        return publishedContent;
    }

    public String getUrl() {
        return url;
    }

    public String getTitle() {
        return title;
    }

    public LocalDate getPublishedDate() {
        return publishedDate;
    }

    public String getContent() {
        return content;
    }

    @Nullable
    public String getImageUrl() {
        return imageUrl;
    }

    @Nullable
    public List<CollectionCategory> getCategories() {
        return categories;
    }

    @Nullable
    public String getSummary() {
        return summary;
    }
}
