package com.nocommittoday.techswipe.content.infrastructure.mysql;

import com.nocommittoday.techswipe.content.domain.TechContentProviderType;
import com.nocommittoday.techswipe.core.infrastructure.mysql.BaseSoftDeleteEntity;
import com.nocommittoday.techswipe.image.infrastructure.mysql.ImageEntity;
import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "icon_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private ImageEntity icon;
}
