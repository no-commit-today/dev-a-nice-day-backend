package com.devniceday.batch.domain;

public record ContentScrapping(
        Scrapping title,
        Scrapping date,
        Scrapping content,
        ImageScrapping image
) {
}
