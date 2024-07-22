package com.nocommittoday.techswipe.batch.client;

import com.nocommittoday.techswipe.content.domain.TechContentProviderSync;
import com.nocommittoday.techswipe.content.domain.TechContentSync;
import com.nocommittoday.techswipe.image.domain.ImageSync;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestClient;

import java.time.LocalDateTime;

public class SyncQueryRestClient {

    private static final ParameterizedTypeReference<ListResponse<ImageSync>> IMAGE_RESPONSE_TYPE
            = new ParameterizedTypeReference<>() {};

    private static final ParameterizedTypeReference<ListResponse<TechContentProviderSync>> PROVIDER_RESPONSE_TYPE
            = new ParameterizedTypeReference<>() {};

    private static final ParameterizedTypeReference<ListResponse<TechContentSync>> CONTENT_RESPONSE_TYPE
            = new ParameterizedTypeReference<>() {};

    private final RestClient restClient;

    public SyncQueryRestClient(
            final RestClient.Builder restClientBuilder,
            final String baseUrl,
            final String authorization
    ) {
        this.restClient = restClientBuilder
                .baseUrl(baseUrl)
                .defaultHeader(HttpHeaders.AUTHORIZATION, authorization)
                .build();
    }

    public ListResponse<ImageSync> getImageList(
            final LocalDateTime from, final LocalDateTime to, int page, int size) {
        return restClient.get()
                .uri("/api/image/admin/sync-images", uriBuilder -> uriBuilder
                        .queryParam("from", from)
                        .queryParam("to", to)
                        .queryParam("page", page)
                        .queryParam("size", size)
                        .build())
                .retrieve()
                .body(IMAGE_RESPONSE_TYPE);
    }

    public ListResponse<TechContentProviderSync> getProviderList(
            final LocalDateTime from, final LocalDateTime to, int page, int size) {
        return restClient.get()
                .uri("/api/content/admin/sync-providers", uriBuilder -> uriBuilder
                        .queryParam("from", from)
                        .queryParam("to", to)
                        .queryParam("page", page)
                        .queryParam("size", size)
                        .build())
                .retrieve()
                .body(PROVIDER_RESPONSE_TYPE);
    }

    public ListResponse<TechContentSync> getContentList(
            final LocalDateTime from, final LocalDateTime to, int page, int size) {
        return restClient.get()
                .uri("/api/content/admin/sync-contents", uriBuilder -> uriBuilder
                        .queryParam("from", from)
                        .queryParam("to", to)
                        .queryParam("page", page)
                        .queryParam("size", size)
                        .build())
                .retrieve()
                .body(CONTENT_RESPONSE_TYPE);
    }
}
