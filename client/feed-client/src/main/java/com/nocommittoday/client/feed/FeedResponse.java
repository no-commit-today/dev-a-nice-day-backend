package com.nocommittoday.client.feed;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.util.List;

public record FeedResponse(
        String link,
        String title,
        @Nullable String iconUrl,
        List<Entry> entries
) {
    public record Entry(
            String link,
            String title,
            LocalDate date,
            @Nullable String content
    ) {
    }
}
