package com.nocommittoday.techswipe.infrastructure.user;

import com.nocommittoday.techswipe.domain.user.AccessToken;
import com.nocommittoday.techswipe.domain.user.AccessTokenDecoded;
import com.nocommittoday.techswipe.domain.user.UserId;
import com.nocommittoday.techswipe.domain.user.exception.UserAuthenticationFailureException;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import static java.time.temporal.ChronoUnit.SECONDS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class JwtAccessTokenTest {

    @Test
    void encoder로_생성한_AccessToken을_decoder로_decoding_할_수_있다() {
        // given
        Clock clock = Clock.fixed(Instant.now(), ZoneId.systemDefault());
        AccessTokenJws accessTokenJws = AccessTokenJws.generate();
        JwtAccessTokenEncoder jwtAccessTokenEncoder = new JwtAccessTokenEncoder(accessTokenJws, clock, 3000);
        JwtAccessTokenDecoder jwtAccessTokenDecoder = new JwtAccessTokenDecoder(accessTokenJws, clock);

        // when
        AccessToken accessToken = jwtAccessTokenEncoder.encode(new UserId(1L));
        AccessTokenDecoded accessTokenDecoded = jwtAccessTokenDecoder.decode(accessToken.getValue());

        // then
        LocalDateTime issuedAt = LocalDateTime.now(clock).truncatedTo(SECONDS);
        assertThat(accessToken.getIssuedAt()).isEqualTo(issuedAt);
        assertThat(accessToken.getExpiresAt()).isEqualTo(issuedAt.plusSeconds(3000));
        assertThat(accessToken.getUserId()).isEqualTo(new UserId(1L));

        AccessToken verified = accessTokenDecoded.verify();
        assertThat(verified.getValue()).isEqualTo(accessToken.getValue());
        assertThat(verified.getIssuedAt()).isEqualTo(accessToken.getIssuedAt());
        assertThat(verified.getExpiresAt()).isEqualTo(accessToken.getExpiresAt());
        assertThat(verified.getUserId()).isEqualTo(accessToken.getUserId());
    }

    @Test
    void 잘못된_값을_디코딩할_경우_검증이_실패한다() {
        // given
        Clock clock = Clock.fixed(Instant.now(), ZoneId.systemDefault());
        AccessTokenJws accessTokenJws = AccessTokenJws.generate();
        JwtAccessTokenDecoder jwtAccessTokenDecoder = new JwtAccessTokenDecoder(accessTokenJws, clock);

        // when
        AccessTokenDecoded accessTokenDecoded = jwtAccessTokenDecoder.decode("invalid-jwt-token");

        // then
        assertThatThrownBy(accessTokenDecoded::verify)
                .isInstanceOf(UserAuthenticationFailureException.class);
    }

    @Test
    void 만료된_토큰을_디코딩할_경우_검증이_실패한다() {
        // given
        int expiration = 1000;
        // JWT 검증 내부에 clockSkew 있어서 1분 까지는 오차 허용됨 -> org.springframework.security.oauth2.jwt.JwtTimestampValidator
        Clock encoderClock = Clock.fixed(Instant.now().minusSeconds(expiration + 100), ZoneId.systemDefault());
        AccessTokenJws accessTokenJws = AccessTokenJws.generate();
        JwtAccessTokenEncoder jwtAccessTokenEncoder = new JwtAccessTokenEncoder(accessTokenJws, encoderClock, expiration);

        Clock decoderClock = Clock.fixed(Instant.now(), ZoneId.systemDefault());
        JwtAccessTokenDecoder jwtAccessTokenDecoder = new JwtAccessTokenDecoder(accessTokenJws, decoderClock);

        // when
        AccessToken accessToken = jwtAccessTokenEncoder.encode(new UserId(1L));
        AccessTokenDecoded accessTokenDecoded = jwtAccessTokenDecoder.decode(accessToken.getValue());

        // then
        assertThatThrownBy(accessTokenDecoded::verify)
                .isInstanceOf(UserAuthenticationFailureException.class);
    }
}
