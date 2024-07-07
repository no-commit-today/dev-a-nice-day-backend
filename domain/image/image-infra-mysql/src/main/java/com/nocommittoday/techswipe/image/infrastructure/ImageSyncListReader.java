package com.nocommittoday.techswipe.image.infrastructure;

import com.nocommittoday.techswipe.core.domain.vo.PageParam;
import com.nocommittoday.techswipe.image.domain.Image;
import com.nocommittoday.techswipe.image.storage.mysql.ImageEntity;
import com.nocommittoday.techswipe.image.storage.mysql.ImageJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ImageSyncListReader {

    private final ImageJpaRepository imageJpaRepository;

    public List<Image> getList(final PageParam pageParam, final LocalDateTime from, final LocalDateTime to) {
        return imageJpaRepository.findAllByLastModifiedAtGreaterThanEqualAndLastModifiedAtLessThanAndDeletedIsFalse(
                         PageRequest.of(
                                 pageParam.page() - 1, pageParam.size(),
                                 Sort.by(Sort.Order.desc("lastModifiedAt"))
                         ),
                        from, to
                )
                .stream()
                .map(ImageEntity::toDomain)
                .toList();
    }
}
