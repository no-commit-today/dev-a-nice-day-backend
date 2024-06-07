package com.nocommittoday.techswipe.collection.storage.mysql;

import com.nocommittoday.techswipe.collection.domain.CollectedContent;
import com.nocommittoday.techswipe.collection.domain.enums.CollectionCategory;
import com.nocommittoday.techswipe.collection.domain.enums.CollectionStatus;
import com.nocommittoday.techswipe.collection.domain.enums.CollectionType;
import com.nocommittoday.techswipe.collection.domain.vo.ContentCollect;
import com.nocommittoday.techswipe.content.domain.TechContentProvider;
import com.nocommittoday.techswipe.core.storage.mysql.BaseSoftDeleteEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.util.List;

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

    @Column(name = "provider_id", nullable = false)
    private Long providerId;

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

    public CollectedContent toDomain() {
        return new CollectedContent(
                new CollectedContent.CollectedContentId(id),
                type,
                status,
                categories,
                summary,
                new TechContentProvider.TechContentProviderId(providerId),
                url,
                title,
                publishedDate,
                content,
                imageUrl
        );
    }

    public static CollectedContentEntity from(final ContentCollect contentCollect) {
        return new CollectedContentEntity(
                null,
                contentCollect.type(),
                CollectionStatus.NONE,
                contentCollect.providerId().value(),
                contentCollect.url(),
                contentCollect.title(),
                contentCollect.publishedDate(),
                contentCollect.content(),
                contentCollect.imageUrl(),
                null,
                null
        );
    }
}
