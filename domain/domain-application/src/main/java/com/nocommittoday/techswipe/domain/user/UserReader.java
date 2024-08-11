package com.nocommittoday.techswipe.domain.user;

import com.nocommittoday.techswipe.domain.user.exception.UserNotFoundException;
import com.nocommittoday.techswipe.domain.user.oauth2.OAuth2User;
import com.nocommittoday.techswipe.storage.mysql.user.OAuth2UserEntity;
import com.nocommittoday.techswipe.storage.mysql.user.OAuth2UserEntityJpaRepository;
import org.springframework.stereotype.Component;

@Component
public class UserReader {

    private final OAuth2UserEntityJpaRepository oAuth2UserEntityJpaRepository;

    public UserReader(OAuth2UserEntityJpaRepository oAuth2UserEntityJpaRepository) {
        this.oAuth2UserEntityJpaRepository = oAuth2UserEntityJpaRepository;
    }

    public User get(OAuth2User oAuth2User) {
        OAuth2UserEntity oAuth2UserEntity = oAuth2UserEntityJpaRepository
                .findByOAuth2ProviderAndOAuth2Id(oAuth2User.getProvider(), oAuth2User.getId())
                .filter(OAuth2UserEntity::isUsed)
                .orElseThrow(() -> new UserNotFoundException(oAuth2User));
        return oAuth2UserEntity.toUser();
    }
}
