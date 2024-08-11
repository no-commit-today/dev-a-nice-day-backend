package com.nocommittoday.techswipe.infrastructure.user;

import com.nimbusds.jose.jwk.source.ImmutableSecret;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import com.nocommittoday.techswipe.domain.core.UuidHolder;
import com.nocommittoday.techswipe.domain.user.RefreshToken;
import com.nocommittoday.techswipe.domain.user.RefreshTokenId;
import com.nocommittoday.techswipe.domain.user.UserId;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

import static java.time.temporal.ChronoUnit.SECONDS;

public class JwtRefreshTokenEncoder {

    private final JwtEncoder jwtEncoder;

    private final JwsHeader jwsHeader;

    private final Clock clock;

    private final UuidHolder uuidHolder;

    private final long expiration;

    public JwtRefreshTokenEncoder(RefreshTokenJws refreshTokenJws, Clock clock, UuidHolder uuidHolder, long expiration) {
        JWKSource<SecurityContext> secret = new ImmutableSecret<>(refreshTokenJws.getSecretKey());
        this.jwtEncoder = new NimbusJwtEncoder(secret);
        this.jwsHeader = JwsHeader.with(refreshTokenJws.getAlgorithm()).build();
        this.clock = clock;
        this.uuidHolder = uuidHolder;
        this.expiration = expiration;
    }

    public RefreshToken encode(UserId userId) {
        RefreshTokenId id = new RefreshTokenId(uuidHolder.random());
        Instant issuedAt = clock.instant().truncatedTo(SECONDS);
        Instant expiresAt = issuedAt.plusSeconds(expiration);
        JwtClaimsSet jwtClaimsSet = JwtClaimsSet.builder()
                .subject(String.valueOf(userId.value()))
                .id(id.value().toString())
                .issuer("techswipe")
                .issuedAt(issuedAt)
                .expiresAt(expiresAt)
                .build();
        JwtEncoderParameters jwtEncoderParameters = JwtEncoderParameters.from(jwsHeader, jwtClaimsSet);
        Jwt jwt = jwtEncoder.encode(jwtEncoderParameters);

        return new RefreshToken(
                jwt.getTokenValue(),
                new RefreshTokenId(UUID.fromString(jwt.getId())),
                new UserId(Long.parseLong(jwt.getSubject())),
                LocalDateTime.ofInstant(Objects.requireNonNull(jwt.getIssuedAt()), clock.getZone()),
                LocalDateTime.ofInstant(Objects.requireNonNull(jwt.getExpiresAt()), clock.getZone())
        );
    }

}
