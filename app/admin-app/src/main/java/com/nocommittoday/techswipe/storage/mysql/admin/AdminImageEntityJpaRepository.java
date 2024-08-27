package com.nocommittoday.techswipe.storage.mysql.admin;

import com.nocommittoday.techswipe.storage.mysql.image.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminImageEntityJpaRepository extends JpaRepository<ImageEntity, Long> {

}
