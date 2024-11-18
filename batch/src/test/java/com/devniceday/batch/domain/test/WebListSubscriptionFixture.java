package com.devniceday.batch.domain.test;

import com.devniceday.batch.domain.ContentScrapping;
import com.devniceday.batch.domain.ListScrapping;
import com.devniceday.batch.domain.WebListSubscription;
import jakarta.annotation.Nullable;

public class WebListSubscriptionFixture {

    @Nullable
    private ListScrapping listScrapping;

    @Nullable
    private ContentScrapping contentScrapping;


    public static WebListSubscription create() {
        return new WebListSubscriptionFixture().build();
    }

    public WebListSubscriptionFixture listScrapping(ListScrapping listScrapping) {
        this.listScrapping = listScrapping;
        return this;
    }

    public WebListSubscriptionFixture contentScrapping(ContentScrapping contentScrapping) {
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
            listScrapping = ListScrappingFixture.create();
        }
        if (contentScrapping == null) {
            contentScrapping = ContentScrappingFixture.create();
        }
    }
}
