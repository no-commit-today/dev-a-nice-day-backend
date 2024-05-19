package com.nocommittoday.techswipe.batch.model;

import jakarta.annotation.Nullable;

public record TechBlogProfile(
        String title,
        @Nullable String iconUrl
) {

    public record Icon(
            int width,
            int height,
            String url
    ) implements Comparable<Icon> {

        @Override
        public int compareTo(final Icon o) {
            if (this.width() == o.width()) {
                return Integer.compare(this.height(), o.height());
            }
            return Integer.compare(this.width(), o.width());
        }
    }
}
