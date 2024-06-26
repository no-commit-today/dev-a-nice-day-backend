package com.nocommittoday.techswipe.subscription.infrastructure;

import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.NodeVisitor;
import org.springframework.stereotype.Component;

@Component
public class HtmlTagCleaner {

    public String clean(final String html) {
        final Document document = Jsoup.parse(html);
        return clean(document);
    }

    public String clean(final Document document) {
        final Element element = document.body();
        return clean(element);
    }

    public String clean(final Element element) {
        element.select("style").remove();

        final StringBuilder sb = new StringBuilder();
        element.traverse(new TagCleanVisitor(sb));

        return sb.toString();
    }

    @RequiredArgsConstructor
    private static class TagCleanVisitor implements NodeVisitor {

        private final StringBuilder text;

        public void head(final Node node, final int depth) {
            if (node instanceof final Element element) {
                if (element.tag().isBlock() && !text.isEmpty()) {
                    text.append("\n");
                }
            }
        }

        public void tail(final Node node, final int depth) {
            if (node instanceof final Element element) {
                if (element.tag().isBlock() && !"br".equals(element.tagName())) {
                    text.append("\n");
                }
            } else if (node instanceof final TextNode textNode) {
                text.append(textNode.getWholeText());
            }
        }
    }
}
