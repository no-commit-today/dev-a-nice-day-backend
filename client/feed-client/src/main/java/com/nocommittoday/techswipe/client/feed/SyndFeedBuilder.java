package com.nocommittoday.techswipe.client.feed;

import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;

public class SyndFeedBuilder {

    public SyndFeed build(final String xml) {
        try (final InputStream is = new ByteArrayInputStream(xml.getBytes())) {
            return new SyndFeedInput().build(new XmlReader(is));
        } catch (final IOException e) {
            throw new UncheckedIOException(e);
        } catch (final FeedException e) {
            throw new UncheckedFeedException(e);
        }
    }
}
