package com.nocommittoday.techswipe.config;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 참고: https://taetaetae.github.io/2020/03/08/spring-rest-docs-in-spring-boot/#springprofile-에-따라-spring-rest-docs-url-을-접근-가능불가능-할-수-있게-한다
 */
@Profile("prod")
@Component
@WebFilter(urlPatterns = "/docs/**")
public class SpringRestDocsAccessFilter implements Filter {

    @Override
    public void doFilter(
            final ServletRequest servletRequest, final ServletResponse servletResponse, final FilterChain chain
    ) throws IOException, ServletException {
        final HttpServletResponse response = (HttpServletResponse) servletResponse;
        response.sendError(HttpServletResponse.SC_FORBIDDEN);
        chain.doFilter(servletRequest, response);
    }
}
