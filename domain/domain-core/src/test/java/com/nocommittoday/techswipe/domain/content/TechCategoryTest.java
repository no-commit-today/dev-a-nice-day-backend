package com.nocommittoday.techswipe.domain.content;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TechCategoryTest {

    @Test
    void value() {
        // given
        // when
        // then
        assertThat(TechCategory.SERVER).isNotNull();
        assertThat(TechCategory.SERVER.getCode()).isEqualTo("SERVER");
        assertThat(TechCategory.SERVER.getTitle()).isEqualTo("서버 개발");

        assertThat(TechCategory.WEB).isNotNull();
        assertThat(TechCategory.WEB.getCode()).isEqualTo("WEB");
        assertThat(TechCategory.WEB.getTitle()).isEqualTo("웹 개발");

        assertThat(TechCategory.APP).isNotNull();
        assertThat(TechCategory.APP.getCode()).isEqualTo("APP");
        assertThat(TechCategory.APP.getTitle()).isEqualTo("앱 개발");

        assertThat(TechCategory.DATA_ENGINEERING).isNotNull();
        assertThat(TechCategory.DATA_ENGINEERING.getCode()).isEqualTo("DATA_ENGINEERING");
        assertThat(TechCategory.DATA_ENGINEERING.getTitle()).isEqualTo("데이터 엔지니어링");

        assertThat(TechCategory.DEVOPS).isNotNull();
        assertThat(TechCategory.DEVOPS.getCode()).isEqualTo("DEVOPS");
        assertThat(TechCategory.DEVOPS.getTitle()).isEqualTo("데브옵스");

        assertThat(TechCategory.AI).isNotNull();
        assertThat(TechCategory.AI.getCode()).isEqualTo("AI");
        assertThat(TechCategory.AI.getTitle()).isEqualTo("인공지능");

        assertThat(TechCategory.SW_ENGINEERING).isNotNull();
        assertThat(TechCategory.SW_ENGINEERING.getCode()).isEqualTo("SW_ENGINEERING");
        assertThat(TechCategory.SW_ENGINEERING.getTitle()).isEqualTo("SW 엔지니어링");

        assertThat(TechCategory.DEV_TOOL).isNotNull();
        assertThat(TechCategory.DEV_TOOL.getCode()).isEqualTo("DEV_TOOL");
        assertThat(TechCategory.DEV_TOOL.getTitle()).isEqualTo("개발 툴");
    }

}