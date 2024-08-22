package com.nocommittoday.techswipe.batch.domain.subscription;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.LocalDate;
import java.time.ZoneId;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class BatchLocalDateParserTest {

    private com.nocommittoday.techswipe.domain.subscription.LocalDateParser localDateParser;

    @BeforeEach
    void setUp() {
        ZoneId zoneId = ZoneId.systemDefault();
        Clock clock = Clock.fixed(
                LocalDate.of(2024, 6, 17)
                        .atStartOfDay(zoneId).toInstant(),
                zoneId);
        localDateParser = new LocalDateParser(clock);
    }

    @Test
    void 변환_가능한_형식() {
        // given
        // when
        // then
        assertAll(
                () -> assertThat(localDateParser.parse("2024-06-15"))
                        .isEqualTo(LocalDate.of(2024, 6, 15)),
                () -> assertThat(localDateParser.parse("2024. 6. 15"))
                        .isEqualTo(LocalDate.of(2024, 6, 15)),
                () -> assertThat(localDateParser.parse("24.06.15"))
                        .isEqualTo(LocalDate.of(2024, 6, 15)),
                () -> assertThat(localDateParser.parse("2024.6.15."))
                        .isEqualTo(LocalDate.of(2024, 6, 15)),
                () -> assertThat(localDateParser.parse("2024. 6. 15"))
                        .isEqualTo(LocalDate.of(2024, 6, 15)),
                () -> assertThat(localDateParser.parse("2024년 6월 15일"))
                        .isEqualTo(LocalDate.of(2024, 6, 15)),
                () -> assertThat(localDateParser.parse("15 6월 2024"))
                        .isEqualTo(LocalDate.of(2024, 6, 15)),
                () -> assertThat(localDateParser.parse("Jun 15, 2024"))
                        .isEqualTo(LocalDate.of(2024, 6, 15)),
                () -> assertThat(localDateParser.parse("Jun.15.2024"))
                        .isEqualTo(LocalDate.of(2024, 6, 15)),
                () -> assertThat(localDateParser.parse("15 Jun, 2024"))
                        .isEqualTo(LocalDate.of(2024, 6, 15)),
                () -> assertThat(localDateParser.parse("2 days ago"))
                        .isEqualTo(LocalDate.of(2024, 6, 15))
        );
    }
}