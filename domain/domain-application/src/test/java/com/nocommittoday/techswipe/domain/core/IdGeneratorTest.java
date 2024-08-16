package com.nocommittoday.techswipe.domain.core;

import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class IdGeneratorTest {

    @Test
    void ID_를_생성할_수_있다() {
        // given
        Clock clock = Clock.fixed(Instant.now(), ZoneOffset.systemDefault());
        long timestamp = clock.millis();

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
        Clock clock = Clock.fixed(Instant.now(), ZoneOffset.systemDefault());
        long timestamp = clock.millis();

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
        Clock clock = Clock.fixed(Instant.now(), ZoneOffset.systemDefault());
        long timestamp = clock.millis();

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
        Clock clock = Clock.fixed(Instant.now(), ZoneOffset.systemDefault());
        long timestamp = clock.millis() - 1;

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
                .atZone(clock.getZone()).toInstant().toEpochMilli();

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

}