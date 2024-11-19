package com.devniceday.batch.domain;

import javax.annotation.Nullable;
import java.time.LocalDate;

public record ContentInitialization(
        @Nullable String title,
        @Nullable String imageUrl,
        @Nullable LocalDate publishedDate,
        @Nullable String content
) {

    public static ContentInitialization empty() {
        return new ContentInitialization(null, null, null, null);
    }
}
