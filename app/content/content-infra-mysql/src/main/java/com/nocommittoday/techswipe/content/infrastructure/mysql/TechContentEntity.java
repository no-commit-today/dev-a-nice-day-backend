package com.nocommittoday.techswipe.content.infrastructure.mysql;

import com.nocommittoday.techswipe.content.domain.TechContent;
import com.nocommittoday.techswipe.core.infrastructure.mysql.BaseSoftDeleteEntity;
import jakarta.annotation.Nullable;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Table(
        name = "tech_content",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_tech_content__url", columnNames = {"url"})
        },
        indexes = {
                @Index(
                        name = "ix_tech_content__tech_blog_id_published_date",
                        columnList = "tech_blog_id, published_date desc"
                ),
                @Index(name = "ix_tech_content__published_date", columnList = "published_date desc")
        }
)
@Getter
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
public class TechContentEntity extends BaseSoftDeleteEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "provider_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private TechContentProviderEntity provider;

    @Nullable
    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "image_id"))
    private ImageIdEmbeddable imageId;

    @Column(name = "url", length = 500, nullable = false)
    private String url;

    @Column(name = "title", length = 500, nullable = false)
    private String title;

    @Lob
    @Column(name = "content", length = 100_000_000, nullable = false)
    private String content;

    @Lob
    @Column(name = "summary", length = 2_000, nullable = false)
    private String summary;

    @Column(name = "published_date", nullable = false)
    private LocalDate publishedDate;

    @OneToMany(mappedBy = "content", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<TechCategoryEntity> categories = new ArrayList<>();

    public List<TechCategoryEntity> getCategories() {
        return Collections.unmodifiableList(categories);
    }

    public TechContent toDomain() {
        return new TechContent(
                new TechContent.TechContentId(id),
                provider.toDomain(),
                Optional.ofNullable(imageId).map(ImageIdEmbeddable::toDomain).orElse(null),
                url,
                title,
                summary,
                categories.stream().map(TechCategoryEntity::getCategory).toList()
        );
    }
}
