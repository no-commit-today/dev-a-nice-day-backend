package com.devniceday.batch.domain;

import com.devniceday.batch.jsoup.HtmlDocument;
import jakarta.annotation.Nullable;
import org.springframework.stereotype.Component;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;

@Component
public class HtmlScrapper {

    @Nullable
    public String scrapText(HtmlDocument html, Scrapping scrapping) {
        if (ScrappingType.INDEX == scrapping.type()) {
            return html.textByIndex(Objects.requireNonNull(scrapping.indexes()));
        } else if (ScrappingType.SELECTOR == scrapping.type()) {
            return html.textBySelector(Objects.requireNonNull(scrapping.selector()));
        } else if (ScrappingType.TITLE == scrapping.type()) {
            return html.title();
        } else if (ScrappingType.NONE == scrapping.type()) {
            return null;
        }

        throw exception(html, scrapping);
    }

    @Nullable
    public String scrapImage(HtmlDocument html, ImageScrapping scrapping) {
        if (ImageScrappingType.OPEN_GRAPH_IMAGE == scrapping.type()) {
            return html.openGraphImage();
        }

        throw exception(html, scrapping);
    }

    @Nullable
    public String scrapHtml(HtmlDocument html, Scrapping scrapping) {
        if (ScrappingType.INDEX == scrapping.type()) {
            return html.htmlByIndexes(Objects.requireNonNull(scrapping.indexes()));
        } else if (ScrappingType.SELECTOR == scrapping.type()) {
            return html.htmlBySelector(Objects.requireNonNull(scrapping.selector()));
        } else if (ScrappingType.NONE == scrapping.type()) {
            return null;
        }

        throw exception(html, scrapping);
    }

    public List<String> scrapUrls(HtmlDocument html, Scrapping scrapping) {
        return new LinkedHashSet<>(doScrapUrls(html, scrapping))
                .stream().toList();
    }

    private List<String> doScrapUrls(HtmlDocument html, Scrapping scrapping) {
        if (ScrappingType.SELECTOR == scrapping.type()) {
            return html.urlListBySelector(scrapping.selector());
        } else if (ScrappingType.INDEX == scrapping.type()) {
            return html.urlListByIndexes(scrapping.indexes());
        }

        throw exception(html, scrapping);
    }

    private IllegalStateException exception(HtmlDocument html, Scrapping scrapping) {
        return new IllegalStateException("지원하지 않는 스크래핑 타입입니다. uri=" + html.uri() + ", type=" + scrapping.type());
    }

    private IllegalStateException exception(HtmlDocument html, ImageScrapping scrapping) {
        return new IllegalStateException(
                "지원하지 않는 이미지 스크래핑 타입입니다. uri=" + html.uri() + ", type=" + scrapping.type());
    }
}
