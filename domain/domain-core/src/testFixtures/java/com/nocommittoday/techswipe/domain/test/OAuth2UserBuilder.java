package com.nocommittoday.techswipe.domain.test;

import com.nocommittoday.techswipe.domain.user.oauth2.OAuth2Provider;
import com.nocommittoday.techswipe.domain.user.oauth2.OAuth2User;

import javax.annotation.Nullable;

public class OAuth2UserBuilder {

    @Nullable
    private OAuth2Provider provider;

    @Nullable
    private String id;

    public OAuth2UserBuilder() {
    }

    public static OAuth2User create() {
        return new OAuth2UserBuilder().build();
    }

    public OAuth2UserBuilder provider(OAuth2Provider provider) {
        this.provider = provider;
        return this;
    }

    public OAuth2UserBuilder id(String id) {
        this.id = id;
        return this;
    }

    public OAuth2User build() {
        fillRequiredFields();
        return new OAuth2User(
            provider,
            id
        );
    }

    private void fillRequiredFields() {

        if (provider == null) {
            provider = OAuth2Provider.GITHUB;
        }

        if (id == null) {
            id = "id-" + LocalAutoIncrementIdUtils.nextId();
        }
    }
}
