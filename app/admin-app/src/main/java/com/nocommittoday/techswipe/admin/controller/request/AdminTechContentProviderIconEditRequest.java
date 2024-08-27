package com.nocommittoday.techswipe.admin.controller.request;

import org.hibernate.validator.constraints.URL;

public record AdminTechContentProviderIconEditRequest(
        @URL String iconUrl
) {
}
