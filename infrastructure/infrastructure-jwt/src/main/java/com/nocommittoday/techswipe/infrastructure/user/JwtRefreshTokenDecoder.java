package com.nocommittoday.techswipe.infrastructure.user;

import com.nocommittoday.techswipe.domain.user.RefreshToken;
import com.nocommittoday.techswipe.domain.user.RefreshTokenDecoded;
import com.nocommittoday.techswipe.domain.user.RefreshTokenId;
import com.nocommittoday.techswipe.domain.user.UserId;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class JwtRefreshTokenDecoder {

    private final Clock clock;

    private final JwtDecoder jwtDecoder;

    public JwtRefreshTokenDecoder(RefreshTokenJws refreshTokenJws, Clock clock) {
        this.clock = clock;
        this.jwtDecoder = NimbusJwtDecoder.withSecretKey(refreshTokenJws.getSecretKey())
                .macAlgorithm(refreshTokenJws.getAlgorithm())
                .build();
    }

    public RefreshTokenDecoded decode(String jwtValue) {
        try {
            Jwt jwt = jwtDecoder.decode(jwtValue);
            RefreshToken refreshToken = new RefreshToken(
                    jwt.getTokenValue(),
                    new RefreshTokenId(UUID.fromString(Objects.requireNonNull(jwt.getId()))),
                    new UserId(Long.parseLong(Objects.requireNonNull(jwt.getSubject()))),
                    LocalDateTime.ofInstant(Objects.requireNonNull(jwt.getIssuedAt()), clock.getZone()),
                    LocalDateTime.ofInstant(Objects.requireNonNull(jwt.getExpiresAt()), clock.getZone())
            );
            return RefreshTokenDecoded.valid(
                    refreshToken
            );
        } catch (JwtException ex) {
            return RefreshTokenDecoded.invalid(ex);
        }
    }
}
