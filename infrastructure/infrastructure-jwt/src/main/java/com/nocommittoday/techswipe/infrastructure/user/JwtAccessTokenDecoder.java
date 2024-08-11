package com.nocommittoday.techswipe.infrastructure.user;

import com.nocommittoday.techswipe.domain.user.AccessToken;
import com.nocommittoday.techswipe.domain.user.AccessTokenDecoded;
import com.nocommittoday.techswipe.domain.user.UserId;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Objects;

public class JwtAccessTokenDecoder {

    private final Clock clock;

    private final JwtDecoder jwtDecoder;

    public JwtAccessTokenDecoder(AccessTokenJws accessTokenJws, Clock clock) {
        this.clock = clock;
        this.jwtDecoder = NimbusJwtDecoder.withSecretKey(accessTokenJws.getSecretKey())
                .macAlgorithm(accessTokenJws.getAlgorithm())
                .build();
    }

    public AccessTokenDecoded decode(String jwtValue) {
        try {
            Jwt jwt = jwtDecoder.decode(jwtValue);
            AccessToken accessToken = new AccessToken(
                    jwt.getTokenValue(),
                    new UserId(Long.parseLong(jwt.getSubject())),
                    LocalDateTime.ofInstant(Objects.requireNonNull(jwt.getIssuedAt()), clock.getZone()),
                    LocalDateTime.ofInstant(Objects.requireNonNull(jwt.getExpiresAt()), clock.getZone())
            );
            return AccessTokenDecoded.valid(
                    accessToken
            );
        } catch (JwtException ex) {
            return AccessTokenDecoded.invalid(ex);
        }
    }
}
