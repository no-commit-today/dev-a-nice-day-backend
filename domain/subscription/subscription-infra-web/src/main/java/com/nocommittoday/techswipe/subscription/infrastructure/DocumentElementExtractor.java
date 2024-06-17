package com.nocommittoday.techswipe.subscription.infrastructure;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class DocumentElementExtractor {

    public Element extractByIndex(final Document document, final List<Integer> indexes) {
        Element element = document.body();
        log.trace("document={}", document.html());
        for (int i = 0; i < indexes.size(); i++) {
            final int index = indexes.get(i);
            element = element.child(index);
            if (log.isTraceEnabled()) {
                log.trace("indexes[{}]={}, indexes={}, \n{}", i, index, indexes, element.html());
            }
        }
        return element;
    }

    public Element extractBySelector(final Document document, final String selector) {
        final Elements elements = document.body().select(selector);
        log.trace("document={}", document.html());
        if (log.isTraceEnabled()) {
            log.trace("selector={}, \n{}", selector, elements.html());
        }
        return elements.first();
    }
}
