package com.devniceday.batch.jsoup;

public class HtmlDocumentScrapException extends RuntimeException {

    public HtmlDocumentScrapException() {
    }

    public HtmlDocumentScrapException(String message) {
        super(message);
    }

    public HtmlDocumentScrapException(Throwable cause) {
        super(cause);
    }

    public HtmlDocumentScrapException(String message, Throwable cause) {
        super(message, cause);
    }
}
