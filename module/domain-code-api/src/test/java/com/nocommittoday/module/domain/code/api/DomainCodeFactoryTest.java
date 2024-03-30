package com.nocommittoday.module.domain.code.api;

import com.nocommittoday.module.domain.code.DomainCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class DomainCodeFactoryTest {

    private DomainCodeFactory domainCodeFactory;

    @BeforeEach
    void setUp() {
        domainCodeFactory = new DomainCodeFactory();
    }

    @Test
    void get() {
        // given
        domainCodeFactory.put(TestEnum1.class.getSimpleName(), TestEnum1.class);

        // when
        List<DomainCodeValue> testEnumValues = domainCodeFactory.get("TestEnum1");

        // then
        assertThat(testEnumValues)
                .hasSize(2)
                .containsExactly(DomainCodeValue.of(TestEnum1.TEST1_1), DomainCodeValue.of(TestEnum1.TEST1_2));
    }

    @Test
    void getList() {
        // given
        domainCodeFactory.put(TestEnum1.class.getSimpleName(), TestEnum1.class);
        domainCodeFactory.put(TestEnum2.class.getSimpleName(), TestEnum2.class);

        // when
        Map<String, List<DomainCodeValue>> enumValueMap = domainCodeFactory.get(List.of("TestEnum1", "TestEnum2"));

        // then
        assertAll(
                () -> assertThat(enumValueMap.keySet())
                        .hasSize(2)
                        .containsExactly("TestEnum1", "TestEnum2"),
                () -> assertThat(enumValueMap.get("TestEnum1"))
                        .hasSize(2)
                        .containsExactly(
                                DomainCodeValue.of(TestEnum1.TEST1_1), DomainCodeValue.of(TestEnum1.TEST1_2)),
                () -> assertThat(enumValueMap.get("TestEnum2"))
                        .hasSize(2)
                        .containsExactly(
                                DomainCodeValue.of(TestEnum2.TEST2_1), DomainCodeValue.of(TestEnum2.TEST2_2))
        );
    }

    @Test
    void getAll() {
        // given
        domainCodeFactory.put(TestEnum1.class.getSimpleName(), TestEnum1.class);
        domainCodeFactory.put(TestEnum2.class.getSimpleName(), TestEnum2.class);

        // when
        Map<String, List<DomainCodeValue>> enumValueMap = domainCodeFactory.getAll();

        // then
        assertAll(
                () -> assertThat(enumValueMap.keySet())
                        .hasSize(2)
                        .containsExactly("TestEnum1", "TestEnum2"),
                () -> assertThat(enumValueMap.get("TestEnum1"))
                        .hasSize(2)
                        .containsExactly(
                                DomainCodeValue.of(TestEnum1.TEST1_1), DomainCodeValue.of(TestEnum1.TEST1_2)),
                () -> assertThat(enumValueMap.get("TestEnum2"))
                        .hasSize(2)
                        .containsExactly(
                                DomainCodeValue.of(TestEnum2.TEST2_1), DomainCodeValue.of(TestEnum2.TEST2_2))
        );
    }

    enum TestEnum1 implements DomainCode {

        TEST1_1("테스트1-1"),
        TEST1_2("테스트1-2"),
        ;

        private final String title;

        TestEnum1(final String title) {
            this.title = title;
        }

        @Override
        public String getCode() {
            return name();
        }

        @Override
        public String getTitle() {
            return title;
        }
    }

    enum TestEnum2 implements DomainCode {

        TEST2_1("테스트2-1"),
        TEST2_2("테스트2-2"),
        ;

        private final String title;

        TestEnum2(final String title) {
            this.title = title;
        }

        @Override
        public String getCode() {
            return name();
        }

        @Override
        public String getTitle() {
            return title;
        }
    }
}