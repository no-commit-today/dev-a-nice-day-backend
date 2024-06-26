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
        element.select("style").remove();
        log.debug("인덱스로 추출 시작. url={}", document.baseUri());
        for (int i = 0; i < indexes.size(); i++) {
            final int index = indexes.get(i);
            if (log.isTraceEnabled()) {
                log.trace("url={}, indexes[{}]={} 추출. indexes={}. \n{}",
                        document.baseUri(), i, index, indexes, element.html());
            }
            element = element.child(index);
        }
        return element;
    }

    public Element extractBySelector(final Document document, final String selector) {
        log.debug("셀렉터로 추출 시작. url={}", document.baseUri());
        final Elements elements = document.body().select(selector);
        if (log.isTraceEnabled()) {
            log.trace("url={}, selector={}, \n{}",
                    document.baseUri(), selector, elements.html());
        }
        return elements.first();
    }
}
