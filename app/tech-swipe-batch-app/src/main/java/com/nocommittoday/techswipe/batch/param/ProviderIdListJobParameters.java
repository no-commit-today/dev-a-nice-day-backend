package com.nocommittoday.techswipe.batch.param;

import com.nocommittoday.techswipe.content.domain.TechContentProvider;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
public class ProviderIdListJobParameters {

    public static final String NAME = "ProviderIdListJobParameters";

    private static final Pattern JOB_PARAMETERS_PATTERN = Pattern.compile("" +
            "\\[(?<idParams>\\d+(,\\d+)*)\\]"
    );

    @Nullable
    private List<TechContentProvider.Id> providerIdList;

    @Value("#{jobParameters['provider.ids']}")
    public void setProviderId(@Nullable final String parameter) {
        if (parameter == null) {
            this.providerIdList = null;
            return;
        }
        final Matcher matcher = JOB_PARAMETERS_PATTERN.matcher(parameter);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("provider.ids parameter is invalid: " + parameter);
        }

        this.providerIdList = Arrays.stream(matcher.group("idParams").split(","))
                .distinct()
                .map(Long::valueOf)
                .map(TechContentProvider.Id::new)
                .toList();
    }
}
