package com.nocommittoday.client.feed;

import com.rometools.rome.io.FeedException;

public class UncheckedFeedException extends RuntimeException {

    public UncheckedFeedException(final FeedException cause) {
        super(cause);
    }
}
