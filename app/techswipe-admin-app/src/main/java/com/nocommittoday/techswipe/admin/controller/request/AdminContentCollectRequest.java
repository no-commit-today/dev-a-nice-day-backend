package com.nocommittoday.techswipe.admin.controller.request;

import com.nocommittoday.techswipe.admin.domain.AdminContentCollectCommand;
import com.nocommittoday.techswipe.domain.content.TechContentProviderId;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import org.hibernate.validator.constraints.URL;

import java.time.LocalDate;

public record AdminContentCollectRequest(
        @NotNull @Positive Long providerId,
        @NotNull @URL String url,
        @NotNull @NotBlank String title,
        @NotNull @PastOrPresent LocalDate publishedDate,
        @NotNull @NotBlank String content,
        @URL String imageUrl
) {

    public AdminContentCollectCommand toCommand() {
        return new AdminContentCollectCommand(
                new TechContentProviderId(providerId),
                url,
                title,
                publishedDate,
                content,
                imageUrl
        );
    }
}
