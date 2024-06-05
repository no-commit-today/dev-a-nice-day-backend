package com.nocommittoday.techswipe.content.infrastructure.mysql;

import com.nocommittoday.techswipe.content.domain.TechContentProvider;
import com.nocommittoday.techswipe.content.domain.TechContentProviderType;
import com.nocommittoday.techswipe.core.infrastructure.mysql.BaseSoftDeleteEntity;
import jakarta.annotation.Nullable;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Optional;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Table(
        name = "tech_content_provider",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_tech_content_provider__url", columnNames = {"url"})
        }
)
@Getter
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
public class TechContentProviderEntity extends BaseSoftDeleteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", columnDefinition = "varchar(45)", nullable = false)
    private TechContentProviderType type;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "url", length = 500, nullable = false)
    private String url;

    @Nullable
    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "icon_id"))
    private ImageIdEmbeddable iconId;

    public TechContentProvider toDomain() {
        return new TechContentProvider(
                new TechContentProvider.TechContentProviderId(id),
                type,
                title,
                url,
                Optional.ofNullable(iconId).map(ImageIdEmbeddable::toDomain).orElse(null)
        );
    }
}
