package com.nocommittoday.techswipe.admin.oauth2;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@ConditionalOnProperty(name = "app.oauth2.client.github.enabled", havingValue = "true")
public class LocalGithubOAuth2Controller {


    private final String clientId;
    private final String clientSecret;
    private final String redirectUri;
    private final RestClient restClient;

    public LocalGithubOAuth2Controller(
            @Value("${app.oauth2.client.github.client-id}") String clientId,
            @Value("${app.oauth2.client.github.client-secret}") String clientSecret,
            @Value("${app.oauth2.client.github.redirect-uri}") String redirectUri,
            RestClient.Builder restClientBuilder
    ) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.redirectUri = redirectUri;
        this.restClient = restClientBuilder
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    @GetMapping("/oauth2/authorization/github")
    public ResponseEntity<Void> redirect() {
        String redirectUrl = UriComponentsBuilder.fromHttpUrl("https://github.com/login/oauth/authorize")
                .queryParam("client_id", clientId)
                .queryParam("redirect_uri", redirectUri)
                .queryParam("scope", "read:user")
                .toUriString();

        return ResponseEntity.status(HttpStatus.FOUND)
                .header(HttpHeaders.LOCATION, redirectUrl)
                .build();
    }

    @GetMapping("/login/oauth2/code/github")
    public OAuth2GithubTokenResponse token(@RequestParam("code") String code) {
        URI uri = UriComponentsBuilder.fromHttpUrl("https://github.com/login/oauth/access_token")
                .queryParam("client_id", clientId)
                .queryParam("client_secret", clientSecret)
                .queryParam("code", code)
                .build().toUri();

        return restClient.post()
                .uri(uri)
                .retrieve()
                .body(OAuth2GithubTokenResponse.class);
    }

    public record OAuth2GithubTokenResponse(
            @JsonProperty("access_token") String accessToken,
            String scope,
            @JsonProperty("token_type") String tokenType
    ) {
    }
}
