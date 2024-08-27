package com.nocommittoday.techswipe.code;

import com.nocommittoday.techswipe.domain.core.EnumMapperFactory;
import com.nocommittoday.techswipe.domain.core.EnumMapperValue;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class CodeController {

    private final EnumMapperFactory enumMapperFactory;

    public CodeController(EnumMapperFactory enumMapperFactory) {
        this.enumMapperFactory = enumMapperFactory;
    }

    @GetMapping("/api/code/v1/codes")
    public ResponseEntity<Map<String, List<EnumMapperValue>>> codeList(
            @RequestParam(required = false) List<String> codeTypes
    ) {
        if (codeTypes == null) {
            return ResponseEntity.ok(enumMapperFactory.getAll());
        }
        return ResponseEntity.ok(enumMapperFactory.get(codeTypes));
    }

}
