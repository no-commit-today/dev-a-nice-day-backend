package com.nocommittoday.techswipe.collection.storage.mysql;

import com.nocommittoday.techswipe.collection.domain.CollectedContent;
import com.nocommittoday.techswipe.collection.domain.CollectionCategory;
import com.nocommittoday.techswipe.collection.domain.CollectionStatus;
import com.nocommittoday.techswipe.collection.domain.CollectionType;
import com.nocommittoday.techswipe.content.storage.mysql.TechContentProviderEntity;
import com.nocommittoday.techswipe.core.storage.mysql.BaseSoftDeleteEntity;
import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(
        name = "collected_content",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_collected_content__url", columnNames = {"url"})
        }
)
@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@AllArgsConstructor
public class CollectedContentEntity extends BaseSoftDeleteEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name = "type", columnDefinition = "varchar(45)", nullable = false)
    private CollectionType type;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", columnDefinition = "varchar(45)", nullable = false)
    private CollectionStatus status;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "provider_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private TechContentProviderEntity provider;

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

    public static CollectedContentEntity from(final CollectedContent collectedContent) {
        return new CollectedContentEntity(
                Optional.ofNullable(collectedContent.getId())
                        .map(CollectedContent.CollectedContentId::id).orElse(null),
                collectedContent.getType(),
                collectedContent.getStatus(),
                TechContentProviderEntity.from(collectedContent.getProviderId()),
                collectedContent.getUrl(),
                collectedContent.getTitle(),
                collectedContent.getPublishedDate(),
                collectedContent.getContent(),
                collectedContent.getImageUrl(),
                collectedContent.getCategories(),
                collectedContent.getSummary()
        );
    }

    public CollectedContent toDomain() {
        return new CollectedContent(
                new CollectedContent.CollectedContentId(id),
                type,
                status,
                categories,
                summary,
                provider.toDomainId(),
                url,
                title,
                publishedDate,
                content,
                imageUrl
        );
    }

    public void update(final CollectedContent collectedContent) {
        this.type = collectedContent.getType();
        this.status = collectedContent.getStatus();
        this.provider = TechContentProviderEntity.from(collectedContent.getProviderId());
        this.url = collectedContent.getUrl();
        this.title = collectedContent.getTitle();
        this.publishedDate = collectedContent.getPublishedDate();
        this.content = collectedContent.getContent();
        this.imageUrl = collectedContent.getImageUrl();
        this.categories = collectedContent.getCategories();
        this.summary = collectedContent.getSummary();
    }

}
