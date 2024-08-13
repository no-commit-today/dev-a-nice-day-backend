package com.nocommittoday.techswipe.admin.support.user;

import com.nocommittoday.techswipe.infrastructure.user.JwtAccessTokenDecoder;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class AdminApiUserArgumentResolverConfig implements WebMvcConfigurer {

    private final AdminApiUserChecker adminApiUserChecker;
    private final JwtAccessTokenDecoder jwtAccessTokenDecoder;

    public AdminApiUserArgumentResolverConfig(
            AdminApiUserChecker adminApiUserChecker,
            JwtAccessTokenDecoder jwtAccessTokenDecoder
    ) {
        this.adminApiUserChecker = adminApiUserChecker;
        this.jwtAccessTokenDecoder = jwtAccessTokenDecoder;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new AdminApiUserArgumentResolver(jwtAccessTokenDecoder, adminApiUserChecker));
    }
}
