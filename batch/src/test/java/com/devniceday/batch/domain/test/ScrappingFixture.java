package com.devniceday.batch.domain.test;

import com.devniceday.batch.domain.Scrapping;
import com.devniceday.batch.domain.ScrappingType;
import com.devniceday.test.java.LongIncrementUtil;

import java.util.List;

public class ScrappingFixture {

    public static Scrapping none() {
        return new Scrapping(ScrappingType.NONE, null, null);
    }

    public static Scrapping title() {
        return new Scrapping(ScrappingType.TITLE, null, null);
    }

    public static Scrapping index() {
        return new Scrapping(ScrappingType.INDEX, List.of(
                1, 2, 3
        ), null);
    }

    public static Scrapping selector() {
        return new Scrapping(ScrappingType.SELECTOR, null, "selector-" + LongIncrementUtil.next());
    }
}
