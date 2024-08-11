package com.nocommittoday.techswipe.storage.mysql.user;

import com.nocommittoday.techswipe.domain.user.oauth2.OAuth2Provider;
import com.nocommittoday.techswipe.storage.mysql.core.BaseSoftDeleteEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import javax.annotation.Nullable;

import static jakarta.persistence.ConstraintMode.NO_CONSTRAINT;
import static jakarta.persistence.FetchType.LAZY;

@Entity
@Table(
        name = "oauth2_users",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_userid_oauth2provider_oauth2id",
                        columnNames = {"oauth2_provider", "oauth2_id"}
                )
        }
)
public class OAuth2UserEntity extends BaseSoftDeleteEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "oauth2_provider", columnDefinition = "varchar(25)", nullable = false)
    private OAuth2Provider oAuth2Provider;

    @Column(name = "oauth2_id", length = 100, nullable = false)
    private String oauth2Id;

    @ManyToOne(optional = false, fetch = LAZY)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(NO_CONSTRAINT))
    private UserEntity user;

    protected OAuth2UserEntity() {
    }

    public OAuth2UserEntity(
            @Nullable Long id,
            OAuth2Provider oAuth2Provider,
            String oauth2Id,
            UserEntity user
    ) {
        this.id = id;
        this.oAuth2Provider = oAuth2Provider;
        this.oauth2Id = oauth2Id;
        this.user = user;
    }
}
