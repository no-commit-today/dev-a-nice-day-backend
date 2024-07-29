package com.nocommittoday.techswipe.admin.controller;

import com.nocommittoday.techswipe.collection.service.ContentCollectCommand;
import com.nocommittoday.techswipe.content.domain.TechContentProviderId;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import org.hibernate.validator.constraints.URL;

import java.time.LocalDate;

public record ContentCollectRequest(
        @NotNull @Positive Long providerId,
        @NotNull @URL String url,
        @NotNull @NotBlank String title,
        @NotNull @PastOrPresent LocalDate publishedDate,
        @NotNull @NotBlank String content,
        @URL String imageUrl
) {

    public ContentCollectCommand toCommand() {
        return new ContentCollectCommand(
                new TechContentProviderId(providerId),
                url,
                title,
                publishedDate,
                content,
                imageUrl
        );
    }
}
