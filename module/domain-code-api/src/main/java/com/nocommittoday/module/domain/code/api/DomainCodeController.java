package com.nocommittoday.module.domain.code.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

public class DomainCodeController {

    private final DomainCodeFactory domainCodeFactory;

    public DomainCodeController(final DomainCodeFactory domainCodeFactory) {
        this.domainCodeFactory = domainCodeFactory;
    }

    @GetMapping("/code")
    public Map<String, List<DomainCodeValue>> getCode(@RequestParam(required = false) List<String> codes) {
        if (codes == null || codes.isEmpty()) {
            return domainCodeFactory.getAll();
        }

        return domainCodeFactory.get(codes);
    }
}
