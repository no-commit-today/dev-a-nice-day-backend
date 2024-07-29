package com.nocommittoday.techswipe.admin.controller;

import org.hibernate.validator.constraints.URL;

public record TechContentProviderIconEditRequest(
        @URL String iconUrl
) {
}
