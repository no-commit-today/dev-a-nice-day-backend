package com.nocommittoday.techswipe.admin.support.user;

import com.nocommittoday.techswipe.domain.user.AccessToken;
import com.nocommittoday.techswipe.domain.user.AdminApiUser;
import com.nocommittoday.techswipe.domain.user.exception.AuthenticationRequiredException;
import com.nocommittoday.techswipe.domain.user.exception.NotAllowedException;
import com.nocommittoday.techswipe.infrastructure.user.JwtAccessTokenDecoder;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class AdminApiUserArgumentResolver implements HandlerMethodArgumentResolver {

    private static final String AUTHORIZATION = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";

    private final JwtAccessTokenDecoder jwtAccessTokenDecoder;
    private final AdminApiUserChecker adminApiUserChecker;

    public AdminApiUserArgumentResolver(
            JwtAccessTokenDecoder jwtAccessTokenDecoder, AdminApiUserChecker adminApiUserChecker) {
        this.jwtAccessTokenDecoder = jwtAccessTokenDecoder;
        this.adminApiUserChecker = adminApiUserChecker;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType() == AdminApiUser.class;
    }

    @Override
    public Object resolveArgument(
            MethodParameter parameter, ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        String authorizationHeader = webRequest.getHeader(AUTHORIZATION);
        if (authorizationHeader == null || !authorizationHeader.startsWith(BEARER_PREFIX)) {
            throw new AuthenticationRequiredException();
        }

        String token = authorizationHeader.substring(BEARER_PREFIX.length());
        AccessToken verified = jwtAccessTokenDecoder.decode(token).verify();

        if (!adminApiUserChecker.check(verified.getUserId())) {
            throw new NotAllowedException(verified.getUserId());
        }

        return new AdminApiUser(verified.getUserId());
    }
}
