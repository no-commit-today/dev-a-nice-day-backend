package com.nocommittoday.techswipe.core.infrastructure;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class IdGeneratorTest {

    @Test
    void ID_를_생성할_수_있다() {
        // given
        final SystemClockHolder systemClockHolder = mock(SystemClockHolder.class);
        final long timestamp = LocalDateTime.of(2024, 7, 10, 0, 0, 0)
                .atZone(ZoneOffset.UTC).toInstant().toEpochMilli();
        given(systemClockHolder.millis()).willReturn(timestamp);

        final IdGenerator idGenerator = new IdGenerator(
                systemClockHolder,
                1000,
                timestamp - 1,
                0
        );

        // when
        final long id = idGenerator.nextId();

        // then
        final long[] attrs = idGenerator.parse(id);
        assertThat(attrs[0]).isEqualTo(timestamp);
        assertThat(attrs[1]).isEqualTo(1000);
        assertThat(attrs[2]).isZero();
    }

    @Test
    void 현재_시간이_이전_시간보다_작으면_오류를_발생시킨다() {
        // given
        final SystemClockHolder systemClockHolder = mock(SystemClockHolder.class);
        final long timestamp = LocalDateTime.of(2024, 7, 10, 0, 0, 0)
                .atZone(ZoneOffset.UTC).toInstant().toEpochMilli();
        given(systemClockHolder.millis()).willReturn(timestamp);

        final IdGenerator idGenerator = new IdGenerator(
                systemClockHolder,
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
        final SystemClockHolder systemClockHolder = mock(SystemClockHolder.class);
        final long timestamp = LocalDateTime.of(2024, 7, 10, 0, 0, 0)
                .atZone(ZoneOffset.UTC).toInstant().toEpochMilli();
        given(systemClockHolder.millis()).willReturn(timestamp);

        final IdGenerator idGenerator = new IdGenerator(
                systemClockHolder,
                1000,
                timestamp,
                0
        );

        // when
        final long id = idGenerator.nextId();

        // then
        final long[] attrs = idGenerator.parse(id);
        assertThat(attrs[0]).isEqualTo(timestamp);
        assertThat(attrs[1]).isEqualTo(1000);
        assertThat(attrs[2]).isEqualTo(1);
    }

    @Test
    void 시퀀스가_최대값에_도달하면_다음_millis로_넘어간다() {
        // given
        final SystemClockHolder systemClockHolder = mock(SystemClockHolder.class);
        final long timestamp = LocalDateTime.of(2024, 7, 10, 0, 0, 0)
                .atZone(ZoneOffset.UTC).toInstant().toEpochMilli();
        given(systemClockHolder.millis())
                .willReturn(timestamp)
                .willReturn(timestamp)
                .willReturn(timestamp)
                .willReturn(timestamp + 1);

        final IdGenerator idGenerator = new IdGenerator(
                systemClockHolder,
                1000,
                timestamp,
                (1L << 12) - 1
        );

        // when
        final long id = idGenerator.nextId();

        // then
        final long[] attrs = idGenerator.parse(id);
        assertThat(attrs[0]).isEqualTo(timestamp + 1);
        assertThat(attrs[1]).isEqualTo(1000);
        assertThat(attrs[2]).isZero();
    }
}