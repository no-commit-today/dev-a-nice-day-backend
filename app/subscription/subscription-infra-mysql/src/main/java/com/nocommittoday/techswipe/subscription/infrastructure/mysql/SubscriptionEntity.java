package com.nocommittoday.techswipe.subscription.infrastructure.mysql;

import com.nocommittoday.techswipe.core.infrastructure.mysql.BaseSoftDeleteEntity;
import com.nocommittoday.techswipe.subscription.domain.Subscription;
import com.nocommittoday.techswipe.subscription.domain.enums.SubscriptionType;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Table(
        name = "subscription",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_subscription__provider_id", columnNames = {"provider_id"})
        }
)
@Getter
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
public class SubscriptionEntity extends BaseSoftDeleteEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "provider_id"))
    private TechContentProviderIdEmbeddable providerId;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", columnDefinition = "varchar(45)", nullable = false)
    private SubscriptionType type;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "data", columnDefinition = "json", nullable = false)
    private SubscriptionData data;

    public Subscription toDomain() {
        return new Subscription(
                new Subscription.SubscriptionId(id),
                providerId.toDomain(),
                type,
                data.getRssUrl(),
                data.getAtomUrl(),
                data.getContentCrawlingIndexes(),
                data.getContentCrawlingNeeds(),
                data.getListCrawlings()
        );
    }
}
