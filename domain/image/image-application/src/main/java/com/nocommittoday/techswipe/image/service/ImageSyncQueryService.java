package com.nocommittoday.techswipe.image.service;

import com.nocommittoday.techswipe.core.domain.vo.PageParam;
import com.nocommittoday.techswipe.image.domain.Image;
import com.nocommittoday.techswipe.image.infrastructure.ImageSyncListReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageSyncQueryService {

    private final ImageSyncListReader imageSyncListReader;

    public List<Image> getList(
            final PageParam pageParam, final ImageSyncQueryParam queryParam
    ) {
        return imageSyncListReader.getList(pageParam, queryParam.from(), queryParam.to());
    }
}
