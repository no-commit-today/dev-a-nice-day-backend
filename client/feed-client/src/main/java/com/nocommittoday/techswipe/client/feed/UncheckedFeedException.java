package com.nocommittoday.techswipe.client.feed;

import com.rometools.rome.io.FeedException;

public class UncheckedFeedException extends RuntimeException {

    public UncheckedFeedException(FeedException cause) {
        super(cause);
    }
}
