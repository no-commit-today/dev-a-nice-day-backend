package com.nocommittoday.techswipe.batch.service;

import net.htmlparser.jericho.Renderer;
import net.htmlparser.jericho.Segment;
import net.htmlparser.jericho.Source;
import org.springframework.stereotype.Component;

@Component
public class HtmlTagCleaner {

    public String clean(final String html) {
        final Source source = new Source(html);
        final Segment segment = new Segment(source, 0, source.length());
        final Renderer renderer = new Renderer(segment)
                .setIncludeHyperlinkURLs(false)
                .setMaxLineLength(Integer.MAX_VALUE);
        return renderer.toString();
    }
}
