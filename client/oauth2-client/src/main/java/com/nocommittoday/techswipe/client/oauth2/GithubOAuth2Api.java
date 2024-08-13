package com.nocommittoday.techswipe.client.oauth2;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "oauth2-github-api", url = "https://api.github.com")
public interface GithubOAuth2Api {

    @GetMapping(value = "/user", consumes = MediaType.APPLICATION_JSON_VALUE)
    GithubOAuth2UserResponse getUser(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization);
}
