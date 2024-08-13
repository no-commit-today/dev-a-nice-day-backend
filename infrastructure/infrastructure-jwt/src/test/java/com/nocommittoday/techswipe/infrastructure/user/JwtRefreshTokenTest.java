package com.nocommittoday.techswipe.infrastructure.user;

import com.nocommittoday.techswipe.domain.core.UuidHolder;
import com.nocommittoday.techswipe.domain.user.RefreshToken;
import com.nocommittoday.techswipe.domain.user.RefreshTokenDecoded;
import com.nocommittoday.techswipe.domain.user.RefreshTokenId;
import com.nocommittoday.techswipe.domain.user.UserId;
import com.nocommittoday.techswipe.domain.user.exception.UserAuthenticationFailureException;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

import static java.time.temporal.ChronoUnit.SECONDS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class JwtRefreshTokenTest {

    @Test
    void encoder로_생성한_AccessToken을_decoder로_decoding_할_수_있다() {
        // given
        UuidHolder uuidHolder = mock(UuidHolder.class);
        UUID uuid = UUID.randomUUID();
        given(uuidHolder.random()).willReturn(uuid);
        Clock clock = Clock.fixed(Instant.now(), ZoneId.systemDefault());
        RefreshTokenJws refreshTokenJws = RefreshTokenJws.generate();
        int expiration = 10000;
        JwtRefreshTokenEncoder encoder = new JwtRefreshTokenEncoder(refreshTokenJws, clock, uuidHolder, expiration);
        JwtRefreshTokenDecoder decoder = new JwtRefreshTokenDecoder(refreshTokenJws, clock);

        // when
        RefreshToken token = encoder.encode(new UserId(1L));
        RefreshTokenDecoded decoded = decoder.decode(token.getValue());

        // then
        LocalDateTime issuedAt = LocalDateTime.now(clock).truncatedTo(SECONDS);
        System.out.println(issuedAt);
        assertThat(token.getIssuedAt()).isEqualTo(issuedAt);
        assertThat(token.getExpiresAt()).isEqualTo(issuedAt.plusSeconds(expiration));
        assertThat(token.getUserId()).isEqualTo(new UserId(1L));
        assertThat(token.getId()).isEqualTo(new RefreshTokenId(uuid));

        RefreshToken verified = decoded.verify();
        assertThat(verified.getValue()).isEqualTo(token.getValue());
        assertThat(verified.getIssuedAt()).isEqualTo(token.getIssuedAt());
        assertThat(verified.getExpiresAt()).isEqualTo(token.getExpiresAt());
        assertThat(verified.getUserId()).isEqualTo(token.getUserId());
        assertThat(verified.getId()).isEqualTo(token.getId());
    }

    @Test
    void 잘못된_값을_디코딩할_경우_검증이_실패한다() {
        // given
        Clock clock = Clock.fixed(Instant.now(), ZoneId.systemDefault());
        RefreshTokenJws jws = RefreshTokenJws.generate();
        JwtRefreshTokenDecoder decoder = new JwtRefreshTokenDecoder(jws, clock);

        // when
        RefreshTokenDecoded decoded = decoder.decode("invalid-jwt-token");

        // then
        assertThatThrownBy(decoded::verify)
                .isInstanceOf(UserAuthenticationFailureException.class);
    }

    @Test
    void 만료된_토큰을_디코딩할_경우_검증이_실패한다() {
        // given
        int expiration = 1000;
        // JWT 검증 내부에 clockSkew 있어서 1분 까지는 오차 허용됨 -> org.springframework.security.oauth2.jwt.JwtTimestampValidator
        Clock encoderClock = Clock.fixed(Instant.now().minusSeconds(expiration + 100), ZoneId.systemDefault());
        RefreshTokenJws jws = RefreshTokenJws.generate();
        UuidHolder uuidHolder = mock(UuidHolder.class);
        UUID uuid = UUID.randomUUID();
        given(uuidHolder.random()).willReturn(uuid);
        JwtRefreshTokenEncoder encoder = new JwtRefreshTokenEncoder(jws, encoderClock, uuidHolder, expiration);

        Clock decoderClock = Clock.fixed(Instant.now(), ZoneId.systemDefault());
        JwtRefreshTokenDecoder decoder = new JwtRefreshTokenDecoder(jws, decoderClock);

        // when
        RefreshToken token = encoder.encode(new UserId(1L));
        RefreshTokenDecoded decoded = decoder.decode(token.getValue());

        // then
        assertThatThrownBy(decoded::verify)
                .isInstanceOf(UserAuthenticationFailureException.class);
    }
}
