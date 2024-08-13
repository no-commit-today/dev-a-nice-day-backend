package com.nocommittoday.techswipe.admin.support.user;

import com.nocommittoday.techswipe.storage.mysql.admin.OAuth2UserEntityJpaRepositoryForAdmin;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Configuration
public class AdminUserConfig implements WebMvcConfigurer {

    @Bean
    public AdminApiUserChecker adminApiUserChecker(
            OAuth2UserEntityJpaRepositoryForAdmin oAuth2UserEntityJpaRepositoryForAdmin,
            RestClient.Builder restClientBuilder,
            @Value("${app.admin.user.github-names:}") String adminGithubNames
    ) {
        Set<String> adminGithubNameSet = Arrays.stream(adminGithubNames.split(","))
                .filter(String::isBlank)
                .collect(Collectors.toSet());
        return new AdminApiUserChecker(adminGithubNameSet, oAuth2UserEntityJpaRepositoryForAdmin, restClientBuilder);
    }
}
