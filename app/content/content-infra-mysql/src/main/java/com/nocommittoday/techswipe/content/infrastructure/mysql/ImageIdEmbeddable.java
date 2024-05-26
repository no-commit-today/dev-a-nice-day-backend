package com.nocommittoday.techswipe.content.infrastructure.mysql;

import com.nocommittoday.techswipe.image.domain.Image;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EqualsAndHashCode
@Embeddable
public class ImageIdEmbeddable {

    private Long value;

    public static ImageIdEmbeddable from(Image.ImageId id) {
        return new ImageIdEmbeddable(id.value());
    }

    public Image.ImageId toDomain() {
        return new Image.ImageId(value);
    }
}
