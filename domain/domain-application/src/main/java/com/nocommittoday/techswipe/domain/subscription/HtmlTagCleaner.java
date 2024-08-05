package com.nocommittoday.techswipe.domain.subscription;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.NodeVisitor;
import org.springframework.stereotype.Component;

@Component
public class HtmlTagCleaner {

    public String clean(String html) {
        Document document = Jsoup.parse(html);
        return clean(document);
    }

    public String clean(Document document) {
        Element element = document.body();
        return clean(element);
    }

    public String clean(Element element) {
        element.select("style").remove();

        StringBuilder sb = new StringBuilder();
        element.traverse(new TagCleanVisitor(sb));

        return sb.toString();
    }

    private static class TagCleanVisitor implements NodeVisitor {

        private final StringBuilder text;

        public TagCleanVisitor(StringBuilder text) {
            this.text = text;
        }

        public void head(Node node, int depth) {
            if (node instanceof Element element) {
                if (element.tag().isBlock() && !text.isEmpty()) {
                    text.append("\n");
                }
            }
        }

        public void tail(Node node, int depth) {
            if (node instanceof Element element) {
                if (element.tag().isBlock() && !"br".equals(element.tagName())) {
                    text.append("\n");
                }
            } else if (node instanceof TextNode textNode) {
                text.append(textNode.getWholeText());
            }
        }
    }
}
