package com.nocommittoday.techswipe.client.file;

import java.io.File;

public record FileResponse(
        File file,
        String contentType
) {
}
