package com.nocommittoday.module.domain.code.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

public class DomainCodeController {

    private final EnumMapperFactory enumMapperFactory;

    public DomainCodeController(final EnumMapperFactory enumMapperFactory) {
        this.enumMapperFactory = enumMapperFactory;
    }

    @GetMapping("/code")
    public Map<String, List<EnumMapperValue>> getCode(@RequestParam(required = false) List<String> codes) {
        if (codes == null || codes.isEmpty()) {
            return enumMapperFactory.getAll();
        }

        return enumMapperFactory.get(codes);
    }
}
