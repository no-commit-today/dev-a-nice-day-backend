package com.nocommittoday.techswipe.batch.client;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestClient;

import java.time.LocalDateTime;

public class ImageSyncQueryRestClient {

    private static final ParameterizedTypeReference<ListResponse<ImageSyncQueryResponse>> RESPONSE_TYPE = new ParameterizedTypeReference<>() {};

    private final RestClient restClient;

    public ImageSyncQueryRestClient(
            final RestClient.Builder restClientBuilder,
            final String baseUrl
    ) {
        this.restClient = restClientBuilder
                .baseUrl(baseUrl)
                .build();
    }

    public ListResponse<ImageSyncQueryResponse> getList(
            final LocalDateTime from, final LocalDateTime to, int page, int size) {
        return restClient.get()
                .uri("/api/image/admin/sync-images", uriBuilder -> uriBuilder
                        .queryParam("from", from)
                        .queryParam("to", to)
                        .queryParam("page", page)
                        .queryParam("size", size)
                        .build())
                .retrieve()
                .body(RESPONSE_TYPE);
    }
}
