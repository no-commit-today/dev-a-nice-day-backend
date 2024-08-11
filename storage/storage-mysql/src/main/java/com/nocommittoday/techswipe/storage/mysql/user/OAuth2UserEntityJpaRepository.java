package com.nocommittoday.techswipe.storage.mysql.user;

import com.nocommittoday.techswipe.domain.user.oauth2.OAuth2Provider;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OAuth2UserEntityJpaRepository extends JpaRepository<OAuth2UserEntity, Long> {

    boolean existsByOauth2ProviderAndOauth2Id(OAuth2Provider oauth2Provider, String oauth2Id);

    Optional<OAuth2UserEntity> findByOauth2ProviderAndOauth2Id(OAuth2Provider oauth2Provider, String oauth2Id);
}
