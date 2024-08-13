package com.nocommittoday.techswipe.infrastructure.user;

import com.nimbusds.jose.jwk.source.ImmutableSecret;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import com.nocommittoday.techswipe.domain.user.AccessToken;
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

import static java.time.temporal.ChronoUnit.SECONDS;

public class JwtAccessTokenEncoder {

    private final JwtEncoder jwtEncoder;

    private final JwsHeader jwsHeader;

    private final Clock clock;

    private final long expiration;

    public JwtAccessTokenEncoder(AccessTokenJws accessTokenJws, Clock clock, long expiration) {
        JWKSource<SecurityContext> secret = new ImmutableSecret<>(accessTokenJws.getSecretKey());
        this.jwtEncoder = new NimbusJwtEncoder(secret);
        this.jwsHeader = JwsHeader.with(accessTokenJws.getAlgorithm()).build();
        this.clock = clock;
        this.expiration = expiration;
    }

    public AccessToken encode(UserId userId) {

        Instant issuedAt = clock.instant().truncatedTo(SECONDS);
        Instant expiresAt = issuedAt.plusSeconds(expiration);
        JwtClaimsSet jwtClaimsSet = JwtClaimsSet.builder()
                .subject(String.valueOf(userId.value()))
                .issuer("techswipe")
                .issuedAt(issuedAt)
                .expiresAt(expiresAt)
                .build();
        JwtEncoderParameters jwtEncoderParameters = JwtEncoderParameters.from(jwsHeader, jwtClaimsSet);
        Jwt jwt = jwtEncoder.encode(jwtEncoderParameters);

        return new AccessToken(
                jwt.getTokenValue(),
                new UserId(Long.parseLong(jwt.getSubject())),
                LocalDateTime.ofInstant(Objects.requireNonNull(jwt.getIssuedAt()), clock.getZone()),
                LocalDateTime.ofInstant(Objects.requireNonNull(jwt.getExpiresAt()), clock.getZone())
        );
    }
}
