package com.devniceday.test.java;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class IdIncrementUtilTest {

    @Test
    void 증가하는_아이디를_생성한다() {
        long id1 = IdIncrementUtil.nextId();
        long id2 = IdIncrementUtil.nextId();
        long id3 = IdIncrementUtil.nextId();

        assertThat(id1).isEqualTo(1);
        assertThat(id2).isEqualTo(2);
        assertThat(id3).isEqualTo(3);
    }
}