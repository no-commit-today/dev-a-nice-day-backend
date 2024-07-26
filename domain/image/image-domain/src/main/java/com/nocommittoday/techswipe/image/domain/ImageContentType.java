package com.nocommittoday.techswipe.image.domain;

import com.nocommittoday.techswipe.image.domain.exception.NotSupportedImageException;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.util.Map;

public record ImageContentType(
        @NonNull String value
) {

    private static final Map<String, String> TYPE_TO_EXT = Map.of(
            "image/jpeg", "jpg",
            "image/png", "png",
            "image/gif", "gif",
            "image/bmp", "bmp",
            "image/webp", "webp",
            "image/svg+xml", "svg",
            "image/tiff", "tif",
            "image/x-icon", "ico",
            "image/vnd.microsoft.icon", "ico"
    );

    public static boolean supports(@Nullable final String contentType) {
        if (contentType == null) {
            return false;
        }
        return TYPE_TO_EXT.containsKey(contentType.split(";")[0]);
    }

    public static boolean supports(@Nullable final String type, @Nullable final String subType) {
        if (type == null || subType == null) {
            return false;
        }
        return TYPE_TO_EXT.containsKey(type + "/" + subType);
    }

    public ImageContentType {
        value = value.split(";")[0];
        if (!supports(value)) {
            throw new NotSupportedImageException(value);
        }
    }

    public ImageContentType(final String type, final String subType) {
        this(type + "/" + subType);
    }

    public String ext() {
        return TYPE_TO_EXT.get(value);
    }
}
