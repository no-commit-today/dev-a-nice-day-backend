package com.nocommittoday.techswipe.storage.mysql.user;

import com.nocommittoday.techswipe.domain.user.oauth2.OAuth2Provider;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OAuth2UserEntityJpaRepository extends JpaRepository<OAuth2UserEntity, Long> {

    boolean existsByOAuth2ProviderAndOAuth2Id(OAuth2Provider provider, String id);
}
