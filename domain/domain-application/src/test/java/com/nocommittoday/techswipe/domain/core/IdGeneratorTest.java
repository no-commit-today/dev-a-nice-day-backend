package com.nocommittoday.techswipe.domain.core;

import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class IdGeneratorTest {

    @Test
    void ID_를_생성할_수_있다() {
        // given
        ZoneId zoneId = ZoneOffset.systemDefault();
        Instant instant = LocalDateTime.of(2024, 7, 10, 0, 0, 0)
                .atZone(zoneId).toInstant();
        long timestamp = instant.toEpochMilli();
        Clock clock = Clock.fixed(instant, zoneId);

        IdGenerator idGenerator = new IdGenerator(
                clock,
                1000,
                timestamp - 1,
                0
        );

        // when
        long id = idGenerator.nextId();

        // then
        long[] attrs = idGenerator.parse(id);
        assertThat(attrs[0]).isEqualTo(timestamp);
        assertThat(attrs[1]).isEqualTo(1000);
        assertThat(attrs[2]).isZero();
    }

    @Test
    void 현재_시간이_이전_시간보다_작으면_오류를_발생시킨다() {
        // given
        ZoneId zoneId = ZoneOffset.systemDefault();
        Instant instant = LocalDateTime.of(2024, 7, 10, 0, 0, 0)
                .atZone(zoneId).toInstant();
        long timestamp = instant.toEpochMilli();

        Clock clock = Clock.fixed(instant, zoneId);

        IdGenerator idGenerator = new IdGenerator(
                clock,
                1000,
                timestamp + 1,
                0
        );

        // when & then
        assertThatThrownBy(idGenerator::nextId)
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void 현재_시간이_이전_시간과_같으면_시퀀스를_증가시킨다() {
        // given
        ZoneId zoneId = ZoneOffset.systemDefault();
        Instant instant = LocalDateTime.of(2024, 7, 10, 0, 0, 0)
                .atZone(zoneId).toInstant();
        long timestamp = instant.toEpochMilli();
        Clock clock = Clock.fixed(instant, zoneId);

        IdGenerator idGenerator = new IdGenerator(
                clock,
                1000,
                timestamp,
                0
        );

        // when
        long id = idGenerator.nextId();

        // then
        long[] attrs = idGenerator.parse(id);
        assertThat(attrs[0]).isEqualTo(timestamp);
        assertThat(attrs[1]).isEqualTo(1000);
        assertThat(attrs[2]).isEqualTo(1);
    }

    @Test
    void 시퀀스가_최대값에_도달하면_다음_millis로_넘어간다() {
        // given
        Clock baseClock = Clock.systemDefaultZone();
        Instant instant = LocalDateTime.of(2024, 7, 10, 0, 0, 0)
                .atZone(baseClock.getZone()).toInstant();
        long timestamp = instant.toEpochMilli();
        Clock clock = Clock.offset(baseClock, Duration.between(baseClock.instant(), instant));

        IdGenerator idGenerator = new IdGenerator(
                clock,
                1000,
                timestamp,
                (1L << 12) - 1
        );

        // when
        long id = idGenerator.nextId();

        // then
        long[] attrs = idGenerator.parse(id);
        assertThat(attrs[0]).isEqualTo(timestamp + 1);
        assertThat(attrs[1]).isEqualTo(1000);
        assertThat(attrs[2]).isZero();
    }

    @Test
    void 시간값이_최대_시간을_초과했을_경우_예외가_발생한다() {
        // given
        Clock clock = Clock.fixed(Instant.now(), ZoneOffset.systemDefault());
        long timestamp = LocalDateTime.of(2100, 7, 10, 0, 0, 0)
                .atZone(ZoneOffset.UTC).toInstant().toEpochMilli();

        IdGenerator idGenerator = new IdGenerator(
                clock,
                1000,
                timestamp - 1,
                0
        );

        // when & then
        assertThatThrownBy(idGenerator::nextId)
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void 밀리초를_통해_첫번째_ID를_생성할_수_있다() {
        // given
        Clock clock = Clock.fixed(Instant.now(), ZoneOffset.systemDefault());
        long timestamp = LocalDateTime.of(2024, 7, 10, 0, 0, 0)
                .atZone(ZoneOffset.UTC).toInstant().toEpochMilli();

        IdGenerator idGenerator = new IdGenerator(
                clock,
                1000,
                timestamp - 1,
                0
        );

        // when
        long id = idGenerator.firstId(timestamp);

        // then
        long[] attrs = idGenerator.parse(id);
        assertThat(attrs[0]).isEqualTo(timestamp);
        assertThat(attrs[1]).isZero();
        assertThat(attrs[2]).isZero();
    }

    @Test
    void LocalDateTime_을_통해_첫번째_ID를_생성할_수_있다() {
        // given
        Clock clock = Clock.fixed(Instant.now(), ZoneOffset.systemDefault());
        long timestamp = LocalDateTime.of(2024, 7, 10, 0, 0, 0)
                .atZone(clock.getZone()).toInstant().toEpochMilli();

        IdGenerator idGenerator = new IdGenerator(
                clock,
                1000,
                timestamp - 1,
                0
        );

        // when
        long id = idGenerator.firstId(LocalDateTime.of(2024, 7, 10, 0, 0, 0));

        // then
        long[] attrs = idGenerator.parse(id);
        assertThat(attrs[0]).isEqualTo(timestamp);
        assertThat(attrs[1]).isZero();
        assertThat(attrs[2]).isZero();
    }

    @Test
    void LocalDate_을_통헤_첫번째_ID를_생성할_수_있다() {
        // given
        Clock clock = Clock.fixed(Instant.now(), ZoneOffset.systemDefault());
        long timestamp = LocalDateTime.of(2024, 7, 10, 0, 0, 0)
                .atZone(clock.getZone()).toInstant().toEpochMilli();

        IdGenerator idGenerator = new IdGenerator(
                clock,
                1000,
                timestamp - 1,
                0
        );

        // when
        long id = idGenerator.firstId(LocalDate.of(2024, 7, 10));

        // then
        long[] attrs = idGenerator.parse(id);
        assertThat(attrs[0]).isEqualTo(timestamp);
        assertThat(attrs[1]).isZero();
        assertThat(attrs[2]).isZero();
    }
}