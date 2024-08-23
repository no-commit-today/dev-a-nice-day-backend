package com.nocommittoday.techswipe.config;

import com.nocommittoday.techswipe.infrastructure.jwt.user.JwtAccessTokenDecoder;
import com.nocommittoday.techswipe.support.ApiUserArgumentResolver;
import com.nocommittoday.techswipe.support.ApiUserOrGuestArgumentResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final JwtAccessTokenDecoder jwtAccessTokenDecoder;

    public WebConfig(JwtAccessTokenDecoder jwtAccessTokenDecoder) {
        this.jwtAccessTokenDecoder = jwtAccessTokenDecoder;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new ApiUserArgumentResolver(jwtAccessTokenDecoder));
        resolvers.add(new ApiUserOrGuestArgumentResolver(jwtAccessTokenDecoder));
    }
}
