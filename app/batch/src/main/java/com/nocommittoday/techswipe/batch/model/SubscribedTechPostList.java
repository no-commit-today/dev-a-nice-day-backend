package com.nocommittoday.techswipe.batch.model;

import java.util.List;

public record SubscribedTechPostList(
        long techBlogId,
        List<SubscribedTechPost> list
) {
}
