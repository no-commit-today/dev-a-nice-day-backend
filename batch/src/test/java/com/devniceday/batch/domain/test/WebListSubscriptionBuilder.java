package com.devniceday.batch.domain.test;

import com.devniceday.batch.domain.ContentScrapping;
import com.devniceday.batch.domain.ListScrapping;
import com.devniceday.batch.domain.WebListSubscription;
import jakarta.annotation.Nullable;

public class WebListSubscriptionBuilder {

    @Nullable
    private ListScrapping listScrapping;

    @Nullable
    private ContentScrapping contentScrapping;


    public static WebListSubscription create() {
        return new WebListSubscriptionBuilder().build();
    }

    public WebListSubscriptionBuilder listScrapping(ListScrapping listScrapping) {
        this.listScrapping = listScrapping;
        return this;
    }

    public WebListSubscriptionBuilder contentScrapping(ContentScrapping contentScrapping) {
        this.contentScrapping = contentScrapping;
        return this;
    }

    public WebListSubscription build() {
        fillRequiredFields();
        return new WebListSubscription(
                listScrapping,
                contentScrapping
        );
    }

    private void fillRequiredFields() {
        if (listScrapping == null) {
            listScrapping = ListScrappingBuilder.create();
        }
        if (contentScrapping == null) {
            contentScrapping = ContentScrappingBuilder.create();
        }
    }
}
