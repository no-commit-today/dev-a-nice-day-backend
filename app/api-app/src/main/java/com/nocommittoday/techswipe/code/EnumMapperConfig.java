package com.nocommittoday.techswipe.code;

import com.nocommittoday.techswipe.domain.content.TechCategory;
import com.nocommittoday.techswipe.domain.core.EnumMapperFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EnumMapperConfig {

    /**
     *  아래와 같이 EnumMapperType 을 구현한 enum 을 enumMapperFactory 에 등록합니다.
     *  enumMapperFactory.put(EnumCodeExample 을 등록할 이름, EnumCodeExample.class);
     */
    @Bean
    public EnumMapperFactory enumMapperFactory() {
        EnumMapperFactory enumMapperFactory = new EnumMapperFactory();
        enumMapperFactory.put(TechCategory.class.getSimpleName(), TechCategory.class);
        return enumMapperFactory;
    }
}
