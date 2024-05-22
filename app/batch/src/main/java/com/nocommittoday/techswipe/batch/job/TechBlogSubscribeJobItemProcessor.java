package com.nocommittoday.techswipe.batch.job;

import com.nocommittoday.techswipe.batch.model.SubscribedTechPostList;
import com.nocommittoday.techswipe.batch.model.TechBlogSubscriptionItem;
import com.nocommittoday.techswipe.batch.model.TechPostItem;
import com.nocommittoday.techswipe.batch.service.TechBlogUrlToIdMapper;
import com.nocommittoday.techswipe.batch.service.TechPostSubscribeService;
import com.nocommittoday.techswipe.domain.rds.subscription.TechBlogSubscription;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.util.StringUtils;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class TechBlogSubscribeJobItemProcessor implements ItemProcessor<TechBlogSubscriptionItem, List<TechPostItem>> {

    private final TechBlogUrlToIdMapper techBlogUrlToIdMapper;
    private final TechPostSubscribeService techPostSubscribeService;

    @Override
    public List<TechPostItem> process(final TechBlogSubscriptionItem item) throws Exception {
        final Long blogId = techBlogUrlToIdMapper.map(item.blogUrl());
        if (blogId == null) {
            log.debug("TechBlog 가 존재하지 않음 url: {}", item.blogUrl());
            return null;
        }
        final TechBlogSubscription subscription = item.toDomain(blogId);
        final SubscribedTechPostList techPostList = techPostSubscribeService.getTechPostList(subscription);
        final List<TechPostItem> processedItems = techPostList.list().stream()
                .map(techPost -> TechPostItem.builder()
                        .techBlogId(blogId)
                        .title(techPost.title())
                        .url(techPost.url())
                        .publishedDate(techPost.publishedDate())
                        .imageUrl(techPost.imageUrl())
                        .content(techPost.content())
                        .build()
                )
                .filter(techPost -> StringUtils.hasText(techPost.content()))
                .toList();
        if (processedItems.isEmpty()) {
            log.debug("TechPost 가 존재하지 않음 blogId: {}", blogId);
            return null;
        }
        return processedItems;
    }
}
