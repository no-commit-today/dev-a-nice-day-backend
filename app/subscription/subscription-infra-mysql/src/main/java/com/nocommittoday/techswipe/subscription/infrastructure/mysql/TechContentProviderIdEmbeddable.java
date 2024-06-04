package com.nocommittoday.techswipe.subscription.infrastructure.mysql;


import com.nocommittoday.techswipe.content.domain.TechContentProvider;
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
public class TechContentProviderIdEmbeddable {

    private Long value;

    public static TechContentProviderIdEmbeddable from(final TechContentProvider.TechContentProviderId id) {
        return new TechContentProviderIdEmbeddable(id.value());
    }

    public TechContentProvider.TechContentProviderId toDomain() {
        return new TechContentProvider.TechContentProviderId(value);
    }
}
