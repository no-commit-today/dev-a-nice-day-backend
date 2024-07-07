package com.nocommittoday.techswipe.batch.client;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestClient;

import java.time.LocalDateTime;

public class SyncQueryRestClient {

    private static final ParameterizedTypeReference<ListResponse<ImageSyncQueryResponse>> IMAGE_RESPONSE_TYPE
            = new ParameterizedTypeReference<>() {};

    private static final ParameterizedTypeReference<ListResponse<TechContentProviderSyncQueryResponse>> PROVIDER_RESPONSE_TYPE
            = new ParameterizedTypeReference<>() {};

    private static final ParameterizedTypeReference<ListResponse<TechContentSyncQueryResponse>> CONTENT_RESPONSE_TYPE
            = new ParameterizedTypeReference<>() {};

    private final RestClient restClient;

    public SyncQueryRestClient(
            final RestClient.Builder restClientBuilder,
            final String baseUrl
    ) {
        this.restClient = restClientBuilder
                .baseUrl(baseUrl)
                .build();
    }

    public ListResponse<ImageSyncQueryResponse> getImageList(
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

    public ListResponse<TechContentProviderSyncQueryResponse> getProviderList(
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

    public ListResponse<TechContentSyncQueryResponse> getContentList(
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
