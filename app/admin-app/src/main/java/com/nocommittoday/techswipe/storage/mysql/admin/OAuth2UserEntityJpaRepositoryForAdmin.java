package com.nocommittoday.techswipe.storage.mysql.admin;

import com.nocommittoday.techswipe.storage.mysql.user.OAuth2UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OAuth2UserEntityJpaRepositoryForAdmin extends JpaRepository<OAuth2UserEntity, Long> {

    List<OAuth2UserEntity> findAllByUserIdAndDeletedIsFalse(Long userId);
}
