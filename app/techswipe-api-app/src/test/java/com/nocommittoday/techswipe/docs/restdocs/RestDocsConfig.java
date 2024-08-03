package com.nocommittoday.techswipe.docs.restdocs;

import org.springframework.boot.test.autoconfigure.restdocs.RestDocsMockMvcConfigurationCustomizer;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;

@TestConfiguration
public class RestDocsConfig {

    @Bean
    public RestDocsMockMvcConfigurationCustomizer restDocsMockMvcConfigurationCustomizer() {
        return configurer -> configurer.operationPreprocessors()
                .withRequestDefaults(
                        prettyPrint()
                )
                .withResponseDefaults(
                        prettyPrint()
                );
    }

    @Bean
    public SecurityFilterChain restDocsSecurityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
                        .anyRequest().permitAll()
                )
                .httpBasic(httpBasic -> httpBasic.disable())
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.disable())
                .logout(logout -> logout.disable())
                .formLogin(formLogin -> formLogin.disable())
                .rememberMe(rememberMe -> rememberMe.disable())
                .requestCache(requestCache -> requestCache.disable())
                .sessionManagement(sessionManagement -> sessionManagement.disable())
                .build();
    }
}
