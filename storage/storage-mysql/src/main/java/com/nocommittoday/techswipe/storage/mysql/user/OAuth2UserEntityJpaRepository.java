package com.nocommittoday.techswipe.storage.mysql.user;

import com.nocommittoday.techswipe.domain.user.oauth2.OAuth2Provider;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OAuth2UserEntityJpaRepository extends JpaRepository<OAuth2UserEntity, Long> {

    boolean existsByOAuth2ProviderAndOAuth2Id(OAuth2Provider provider, String id);

    Optional<OAuth2UserEntity> findByOAuth2ProviderAndOAuth2Id(OAuth2Provider oAuth2Provider, String oAuth2Id);
}
