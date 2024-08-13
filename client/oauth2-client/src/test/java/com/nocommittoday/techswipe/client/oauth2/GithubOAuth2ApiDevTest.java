package com.nocommittoday.techswipe.client.oauth2;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;

@Tag("develop")
@SpringBootApplication
@SpringBootTest
class GithubOAuth2ApiDevTest {

    @Autowired
    private GithubOAuth2Api githubOAuth2Api;

    @Test
    void 호출() {
        var accessToken = "";
        GithubOAuth2UserResponse response = githubOAuth2Api.getUser("Bearer " + accessToken);
        System.out.println(response);
    }
}