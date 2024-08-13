package com.nocommittoday.techswipe.domain.user;

import com.nocommittoday.techswipe.domain.user.exception.AlreadyExistsException;
import com.nocommittoday.techswipe.domain.user.oauth2.OAuth2User;
import com.nocommittoday.techswipe.storage.mysql.user.OAuth2UserEntity;
import com.nocommittoday.techswipe.storage.mysql.user.OAuth2UserEntityJpaRepository;
import com.nocommittoday.techswipe.storage.mysql.user.UserEntity;
import com.nocommittoday.techswipe.storage.mysql.user.UserEntityJpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class UserAppender {

    private final UserEntityJpaRepository userEntityJpaRepository;
    private final OAuth2UserEntityJpaRepository oAuth2UserEntityJpaRepository;

    public UserAppender(
            UserEntityJpaRepository userEntityJpaRepository,
            OAuth2UserEntityJpaRepository oAuth2UserEntityJpaRepository
    ) {
        this.userEntityJpaRepository = userEntityJpaRepository;
        this.oAuth2UserEntityJpaRepository = oAuth2UserEntityJpaRepository;
    }

    @Transactional
    public UserId append(OAuth2User oAuth2User) {
        if (oAuth2UserEntityJpaRepository
                .existsByOauth2ProviderAndOauth2Id(oAuth2User.getProvider(), oAuth2User.getId())) {
            throw new AlreadyExistsException(oAuth2User);
        }
        UserEntity userEntity = userEntityJpaRepository.save(new UserEntity(null));
        oAuth2UserEntityJpaRepository.save(new OAuth2UserEntity(
                oAuth2User.getProvider(),
                oAuth2User.getId(),
                userEntity
        ));
        return new UserId(userEntity.getId());
    }
}
