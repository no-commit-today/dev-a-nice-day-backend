package com.nocommittoday.techswipe.infrastructure.jwt.user;

import com.nocommittoday.techswipe.domain.core.UuidHolder;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.time.Clock;
import java.util.Arrays;
import java.util.List;

@Configuration
@EnableConfigurationProperties(JwtProperties.class)
public class JwtConfig implements InitializingBean {

    private final JwtProperties jwtProperties;
    private final Environment environment;

    public JwtConfig(JwtProperties jwtProperties, Environment environment) {
        this.jwtProperties = jwtProperties;
        this.environment = environment;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        List<String> profiles = Arrays.asList(environment.getActiveProfiles());
        if (!profiles.contains("dev") && !profiles.contains("prod")) {
            return;
        }

        if (jwtProperties.accessToken() == null
                || jwtProperties.accessToken().secret() == null
                || jwtProperties.accessToken().expiration() == null) {
            throw new IllegalStateException("AccessToken 의 secret, expiration 설정이 필요합니다.");
        }

        if (jwtProperties.refreshToken() == null
                || jwtProperties.refreshToken().secret() == null
                || jwtProperties.refreshToken().expiration() == null) {
            throw new IllegalStateException("RefreshToken 의 secret, expiration 설정이 필요합니다.");
        }
    }

    @Bean
    public AccessTokenJws accessTokenJws() {
        return jwtProperties.accessToken().secret() == null ?
                AccessTokenJws.generate() :
                AccessTokenJws.from(jwtProperties.accessToken().secret());
    }

    @Bean
    public JwtAccessTokenEncoder jwtAccessTokenEncoder(AccessTokenJws accessTokenJws, Clock clock) {
        return new JwtAccessTokenEncoder(accessTokenJws, clock, jwtProperties.accessToken().expirationOrDefault());
    }

    @Bean
    public JwtAccessTokenDecoder jwtAccessTokenDecoder(AccessTokenJws accessTokenJws, Clock clock) {
        return new JwtAccessTokenDecoder(accessTokenJws, clock);
    }

    @Bean
    public RefreshTokenJws refreshTokenJws() {
        return jwtProperties.refreshToken().secret() == null ?
                RefreshTokenJws.generate() :
                RefreshTokenJws.from(jwtProperties.refreshToken().secret());
    }

    @Bean
    public JwtRefreshTokenEncoder jwtRefreshTokenEncoder(
            RefreshTokenJws refreshTokenJws, Clock clock, UuidHolder uuidHolder) {
        return new JwtRefreshTokenEncoder(
                refreshTokenJws, clock, uuidHolder, jwtProperties.refreshToken().expirationOrDefault());
    }

    @Bean
    public JwtRefreshTokenDecoder jwtRefreshTokenDecoder(RefreshTokenJws refreshTokenJws, Clock clock) {
        return new JwtRefreshTokenDecoder(refreshTokenJws, clock);
    }
}
