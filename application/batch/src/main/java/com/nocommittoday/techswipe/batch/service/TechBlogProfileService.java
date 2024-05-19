package com.nocommittoday.techswipe.batch.service;

import com.nocommittoday.techswipe.batch.model.TechBlogProfile;
import com.nocommittoday.techswipe.batch.util.JsoupUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

@Service
public class TechBlogProfileService {

    private static final Logger log = LoggerFactory.getLogger(TechBlogProfileService.class);

    private static final int ICON_WIDTH_MINIMUM = 48;

    public TechBlogProfile getProfile(final String techBlogUrl) {
        final Document document = JsoupUtils.getDocument(techBlogUrl);

        final String title = document.title();

        final TreeMap<Integer, TechBlogProfile.Icon> icons = new TreeMap<>();
        final Elements iconElements = Optional.of(document.head().select("link[rel=apple-touch-icon]"))
                .filter(elements -> !elements.isEmpty())
                .orElseGet(() -> document.head().select("link[rel=icon]"));

        for (Element iconElement : iconElements) {
            final String iconUrl = iconElement.attr("abs:href");
            final String sizeAttr = iconElement.attr("sizes");
            final int[] size = Optional.of(sizeAttr)
                    .filter(StringUtils::hasText)
                    .map(attr -> Arrays.stream(attr.split("x"))
                            .mapToInt(Integer::parseInt)
                            .toArray()
                    ).orElseGet(() -> new int[]{0, 0});
            final int width = size[0];
            final int height = size[1];
            icons.put(width, new TechBlogProfile.Icon(width, height, iconUrl));
        }

        log.debug("{}의 모든 아이콘 = {}", title, icons.values());

        final String iconUrl = Optional.ofNullable(icons.ceilingEntry(ICON_WIDTH_MINIMUM))
                .or(() -> Optional.ofNullable(icons.floorEntry(ICON_WIDTH_MINIMUM)))
                .map(Map.Entry::getValue)
                .map(TechBlogProfile.Icon::url)
                .orElse(null);

        log.debug("{}의 선택된 아이콘 = {}", title, iconUrl);

        return new TechBlogProfile(title, iconUrl);
    }
}
