package com.nocommittoday.techswipe.support;

import com.nocommittoday.techswipe.domain.user.AccessToken;
import com.nocommittoday.techswipe.domain.user.ApiUserOrGuest;
import com.nocommittoday.techswipe.infrastructure.jwt.user.JwtAccessTokenDecoder;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class ApiUserOrGuestArgumentResolver implements HandlerMethodArgumentResolver {

    private static final String AUTHORIZATION = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";

    private final JwtAccessTokenDecoder jwtAccessTokenDecoder;

    public ApiUserOrGuestArgumentResolver(JwtAccessTokenDecoder jwtAccessTokenDecoder) {
        this.jwtAccessTokenDecoder = jwtAccessTokenDecoder;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType() == ApiUserOrGuest.class;
    }

    @Override
    public Object resolveArgument(
            MethodParameter parameter, ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        String authorizationHeader = webRequest.getHeader(AUTHORIZATION);
        if (authorizationHeader == null || !authorizationHeader.startsWith(BEARER_PREFIX)) {
            return ApiUserOrGuest.guest();
        }

        String token = authorizationHeader.substring(BEARER_PREFIX.length());
        AccessToken verified = jwtAccessTokenDecoder.decode(token).verify();
        return ApiUserOrGuest.user(verified.getUserId());
    }
}
