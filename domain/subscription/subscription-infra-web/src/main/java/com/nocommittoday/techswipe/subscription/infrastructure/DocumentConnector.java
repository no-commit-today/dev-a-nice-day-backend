package com.nocommittoday.techswipe.subscription.infrastructure;

import com.nocommittoday.techswipe.client.core.ClientResponse;
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

    public ClientResponse<DocumentCrawler> connect(String url) {
        try {
            Document document = Jsoup.connect(url).get();
            DocumentCrawler documentCrawler = new DocumentCrawler(document);
            return ClientResponse.success(documentCrawler);
        } catch (HttpStatusException e) {
            if (HttpStatus.NOT_FOUND.value() == e.getStatusCode()) {
                return ClientResponse.notFound(e);
            }
            return ClientResponse.failed(e);
        } catch (IOException e) {
            return ClientResponse.failed(e);
        }
    }
}
