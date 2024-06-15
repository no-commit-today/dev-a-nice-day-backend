package com.nocommittoday.techswipe.subscription.infrastructure;

import com.nocommittoday.client.core.ClientResponse;
import lombok.RequiredArgsConstructor;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class DocumentConnector {

    public ClientResponse<Document> connect(final String url) {
        try {
            return ClientResponse.success(Jsoup.connect(url).get());
        } catch (final HttpStatusException e) {
            if (HttpStatus.NOT_FOUND.value() == e.getStatusCode()) {
                return ClientResponse.notFound(e);
            }
            return ClientResponse.failed(e);
        } catch (final IOException e) {
            return ClientResponse.failed(e);
        }
    }
}
