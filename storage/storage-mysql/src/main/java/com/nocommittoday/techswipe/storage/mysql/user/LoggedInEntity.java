package com.nocommittoday.techswipe.storage.mysql.user;

import com.nocommittoday.techswipe.domain.user.LoggedIn;
import com.nocommittoday.techswipe.domain.user.LoggedInUser;
import com.nocommittoday.techswipe.domain.user.RefreshTokenId;
import com.nocommittoday.techswipe.domain.user.UserId;
import com.nocommittoday.techswipe.storage.mysql.core.BaseSoftDeleteEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import javax.annotation.Nullable;
import java.time.LocalDateTime;
import java.util.UUID;

import static jakarta.persistence.ConstraintMode.NO_CONSTRAINT;
import static jakarta.persistence.FetchType.LAZY;

@Entity
@Table(
        name = "logged_in",
        indexes = {
                @Index(name = "ix_userid", columnList = "user_id")
        },
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_refreshtokenid", columnNames = "refresh_token_id")
        }
)
public class LoggedInEntity extends BaseSoftDeleteEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = LAZY)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(NO_CONSTRAINT))
    private UserEntity user;

    @Column(name = "refresh_token_id", length = 65, nullable = false)
    private String refreshTokenId;

    @Column(name = "expires_at", nullable = false)
    private LocalDateTime expiresAt;

    protected LoggedInEntity() {
    }

    public LoggedInEntity(
            @Nullable Long id,
            UserEntity user,
            String refreshTokenId,
            LocalDateTime expiresAt
    ) {
        this.id = id;
        this.user = user;
        this.refreshTokenId = refreshTokenId;
        this.expiresAt = expiresAt;
    }

    public LoggedInUser toDomain() {
        return new LoggedInUser(
                new UserId(user.getId()),
                new LoggedIn(
                        new RefreshTokenId(UUID.fromString(refreshTokenId)),
                        expiresAt
                )
        );
    }

    public Long getId() {
        return id;
    }

    public UserEntity getUser() {
        return user;
    }

    public String getRefreshTokenId() {
        return refreshTokenId;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }
}
