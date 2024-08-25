package com.nocommittoday.techswipe.infrastructure.feed;

import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;

@Component
public class SyndFeedBuilder {

    private final SyndFeedInput syndFeedInput = new SyndFeedInput();

    public SyndFeed build(String xml) {
        try (InputStream is = new ByteArrayInputStream(xml.getBytes())) {
            return syndFeedInput.build(new XmlReader(is));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        } catch (FeedException e) {
            throw new UncheckedFeedException(e);
        }
    }
}
