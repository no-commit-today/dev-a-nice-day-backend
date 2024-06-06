package com.nocommittoday.techswipe.content.storage.mysql;

import com.nocommittoday.techswipe.content.domain.TechContent;
import com.nocommittoday.techswipe.core.storage.mysql.BaseSoftDeleteEntity;
import com.nocommittoday.techswipe.image.storage.mysql.ImageEntity;
import jakarta.annotation.Nullable;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
                        name = "ix_tech_content__provider_published_date",
                        columnList = "provider_id, published_date desc"
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
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "image_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private ImageEntity image;

    @Column(name = "url", length = 500, nullable = false)
    private String url;

    @Column(name = "title", length = 500, nullable = false)
    private String title;

    @Column(name = "summary", length = 2_000, nullable = false)
    private String summary;

    @Column(name = "published_date", nullable = false)
    private LocalDate publishedDate;

    @OneToMany(mappedBy = "content", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<TechCategoryEntity> categories = new ArrayList<>();

    public List<TechCategoryEntity> getCategories() {
        return Collections.unmodifiableList(categories);
    }

    public TechContent.TechContentId toDomainId() {
        return new TechContent.TechContentId(id);
    }

    public TechContent toDomain() {
        return new TechContent(
                toDomainId(),
                provider.toDomain(),
                image == null ? null : image.toDomainId(),
                url,
                title,
                summary,
                categories.stream().map(TechCategoryEntity::getCategory).toList()
        );
    }
}
