package com.nocommittoday.techswipe.batch.domain.subscription;

import com.nocommittoday.techswipe.batch.domain.subscription.exception.NotSupportedScrappingException;
import com.nocommittoday.techswipe.domain.subscription.IndexScrapping;
import com.nocommittoday.techswipe.domain.subscription.Scrapping;
import com.nocommittoday.techswipe.domain.subscription.ScrappingType;
import com.nocommittoday.techswipe.domain.subscription.SelectorScrapping;
import com.nocommittoday.techswipe.infrastructure.jsoup.HtmlDocument;

import javax.annotation.Nullable;

public final class DocumentScrappingHelper {

    private DocumentScrappingHelper() {
        throw new UnsupportedOperationException();
    }

    @Nullable
    public static String scrap(HtmlDocument document, Scrapping scrapping) {
        if (ScrappingType.INDEX == scrapping.getType() && scrapping instanceof IndexScrapping indexScrapping) {
            return document.textByIndex(indexScrapping.getIndexes());
        } else if (ScrappingType.SELECTOR == scrapping.getType() && scrapping instanceof SelectorScrapping selectorScrapping) {
            return document.textBySelector(selectorScrapping.getSelector());
        } else if (ScrappingType.TITLE == scrapping.getType()) {
            return document.title();
        } else if (ScrappingType.OPEN_GRAPH_IMAGE == scrapping.getType()) {
            return document.openGraphImage();
        } else if (ScrappingType.NONE == scrapping.getType()) {
            return null;
        }

        throw new NotSupportedScrappingException(scrapping.getType());
    }

    @Nullable
    public static String html(HtmlDocument document, Scrapping scrapping) {
        if (ScrappingType.INDEX == scrapping.getType() && scrapping instanceof IndexScrapping indexScrapping) {
            return document.htmlByIndexes(indexScrapping.getIndexes());
        } else if (ScrappingType.SELECTOR == scrapping.getType() && scrapping instanceof SelectorScrapping selectorScrapping) {
            return document.htmlBySelector(selectorScrapping.getSelector());
        } else if (ScrappingType.NONE == scrapping.getType()) {
            return null;
        }

        throw new NotSupportedScrappingException(scrapping.getType());
    }
}
