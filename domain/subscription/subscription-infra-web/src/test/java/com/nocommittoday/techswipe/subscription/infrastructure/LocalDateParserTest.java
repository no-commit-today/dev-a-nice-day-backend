package com.nocommittoday.techswipe.subscription.infrastructure;

import com.nocommittoday.techswipe.core.infrastructure.LocalDateHolder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class LocalDateParserTest {

    @InjectMocks
    private LocalDateParser localDateParser;

    @Mock
    private LocalDateHolder localDateHolder;

    @Test
    void 변환_가능한_형식() {
        // given
        given(localDateHolder.now()).willReturn(LocalDate.of(2024, 6, 17));

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