package com.nocommittoday.techswipe.admin.support.user;

import com.nocommittoday.techswipe.domain.user.UserId;
import com.nocommittoday.techswipe.domain.user.oauth2.OAuth2Provider;
import com.nocommittoday.techswipe.storage.mysql.admin.OAuth2UserEntityJpaRepositoryForAdmin;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class AdminApiUserChecker {

    private final Set<String> adminGithubNames;
    private final OAuth2UserEntityJpaRepositoryForAdmin oAuth2UserEntityJpaRepositoryForAdmin;
    private final RestClient restClient;
    private final Set<UserId> userIdCache = Collections.synchronizedSet(new HashSet<>());

    public AdminApiUserChecker(
            Set<String> adminGithubNames,
            OAuth2UserEntityJpaRepositoryForAdmin oAuth2UserEntityJpaRepositoryForAdmin,
            RestClient.Builder restClientBuilder) {
        this.adminGithubNames = adminGithubNames;
        this.oAuth2UserEntityJpaRepositoryForAdmin = oAuth2UserEntityJpaRepositoryForAdmin;
        this.restClient = restClientBuilder
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    public boolean check(UserId userId) {
        if (userIdCache.contains(userId)) {
            return true;
        }

        Optional<String> githubIdOpt = oAuth2UserEntityJpaRepositoryForAdmin.findAllByUserIdAndDeletedIsFalse(
                        userId.value())
                .stream()
                .filter(entity -> entity.getOauth2Provider() == OAuth2Provider.GITHUB)
                .findFirst()
                .map(entity -> entity.getOauth2Id());

        if (githubIdOpt.isEmpty()) {
            return false;
        }
        String githubId = githubIdOpt.get();
        String githubName = getGithubName(githubId);
        if (!adminGithubNames.contains(githubName)) {
            return false;
        }

        userIdCache.add(userId);
        return true;
    }

    private String getGithubName(String githubId) {
        return restClient
                .get()
                .uri("https://api.github.com/user/{accountId}", githubId)
                .retrieve()
                .body(GithubUserResponse.class)
                .name();
    }
}
