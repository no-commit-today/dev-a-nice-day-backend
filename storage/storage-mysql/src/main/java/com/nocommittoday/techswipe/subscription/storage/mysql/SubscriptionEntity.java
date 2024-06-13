package com.nocommittoday.techswipe.subscription.storage.mysql;

import com.nocommittoday.techswipe.content.storage.mysql.TechContentProviderEntity;
import com.nocommittoday.techswipe.core.storage.mysql.BaseSoftDeleteEntity;
import com.nocommittoday.techswipe.subscription.domain.Subscription;
import com.nocommittoday.techswipe.subscription.domain.enums.SubscriptionInitType;
import com.nocommittoday.techswipe.subscription.domain.enums.SubscriptionType;
import com.nocommittoday.techswipe.subscription.domain.vo.SubscriptionRegister;
import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import static jakarta.persistence.FetchType.*;
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

    @OneToOne(fetch = LAZY, optional = false)
    @JoinColumn(name = "provider_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private TechContentProviderEntity provider;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", columnDefinition = "varchar(45)", nullable = false)
    private SubscriptionType type;

    @Enumerated(EnumType.STRING)
    @Column(name = "init_type", columnDefinition = "varchar(45)", nullable = false)
    private SubscriptionInitType initType;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "data", columnDefinition = "json", nullable = false)
    private SubscriptionData data;

    public static SubscriptionEntity from(final SubscriptionRegister register) {
        return new SubscriptionEntity(
                null,
                TechContentProviderEntity.from(register.providerId()),
                register.type(),
                register.initType(),
                new SubscriptionData(
                        register.rssUrl(),
                        register.atomUrl(),
                        register.contentCrawling(),
                        register.listCrawlings()
                )
        );
    }

    public Subscription toDomain() {
        return new Subscription(
                new Subscription.SubscriptionId(id),
                provider.toDomainId(),
                type,
                initType,
                data.getRssUrl(),
                data.getAtomUrl(),
                data.getContentCrawling(),
                data.getListCrawlings()
        );
    }
}
