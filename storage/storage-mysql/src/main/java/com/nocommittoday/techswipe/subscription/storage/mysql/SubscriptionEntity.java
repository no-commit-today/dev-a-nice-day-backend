package com.nocommittoday.techswipe.subscription.storage.mysql;

import com.nocommittoday.techswipe.content.domain.TechContentProviderId;
import com.nocommittoday.techswipe.content.storage.mysql.TechContentProviderEntity;
import com.nocommittoday.techswipe.storage.mysql.core.BaseSoftDeleteEntity;
import com.nocommittoday.techswipe.subscription.domain.Subscription;
import com.nocommittoday.techswipe.subscription.domain.SubscriptionId;
import com.nocommittoday.techswipe.subscription.domain.SubscriptionRegister;
import com.nocommittoday.techswipe.subscription.domain.SubscriptionType;
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
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(
        name = "subscription",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_subscription__provider_id", columnNames = {"provider_id"})
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

    @Enumerated(EnumType.STRING)
    @Column(name = "init_type", columnDefinition = "varchar(45)", nullable = false)
    private SubscriptionType initType;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "data", columnDefinition = "json", nullable = false)
    private SubscriptionData data;

    protected SubscriptionEntity() {
    }

    public SubscriptionEntity(
            Long id,
            TechContentProviderEntity provider,
            SubscriptionType type,
            SubscriptionType initType,
            SubscriptionData data
    ) {
        this.id = id;
        this.provider = provider;
        this.type = type;
        this.initType = initType;
        this.data = data;
    }

    public static SubscriptionEntity from(SubscriptionRegister register) {
        return new SubscriptionEntity(
                null,
                TechContentProviderEntity.from(register.providerId()),
                register.type(),
                register.initType(),
                new SubscriptionData(
                        new FeedData(register.feedUrl()),
                        ContentCrawlingData.from(register.contentCrawling()),
                        ListCrawlingListData.from(register.listCrawlings())
                ));
    }

    public void update(SubscriptionRegister register) {
        type = register.type();
        initType = register.initType();
        data = new SubscriptionData(
                new FeedData(register.feedUrl()),
                ContentCrawlingData.from(register.contentCrawling()),
                ListCrawlingListData.from(register.listCrawlings())
        );
    }

    public Subscription toDomain() {
        return new Subscription(
                new SubscriptionId(id),
                provider.getId() != null ? new TechContentProviderId(provider.getId()) : null,
                type,
                initType,
                data.getFeed().getUrl(),
                data.getContentCrawling().toDomain(),
                data.getListCrawlings().toDomain()
        );
    }

    public SubscriptionData getData() {
        return data;
    }

    public Long getId() {
        return id;
    }

    public SubscriptionType getInitType() {
        return initType;
    }

    public TechContentProviderEntity getProvider() {
        return provider;
    }

    public SubscriptionType getType() {
        return type;
    }
}
