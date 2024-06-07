package com.nocommittoday.techswipe.collection.domain.exception;

import com.nocommittoday.techswipe.core.domain.exception.ErrorCodeType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.net.HttpURLConnection;

@Getter
@RequiredArgsConstructor
public enum CollectionErrorCode implements ErrorCodeType {
    CATEGORIZE_UNABLE("COLLECTION-001", "카테고리화 할 수 없습니다.", HttpURLConnection.HTTP_BAD_REQUEST),
    SUMMARIZE_UNABLE("COLLECTION-002", "요약할 수 없습니다.", HttpURLConnection.HTTP_BAD_REQUEST),
    NOT_FOUND("COLLECTION-003", "수집된 컨텐츠를 찾을 수 없습니다.", HttpURLConnection.HTTP_BAD_REQUEST),
    PROMPT_NOT_FOUND("COLLECTION-004", "프롬프트를 찾을 수 없습니다.", HttpURLConnection.HTTP_BAD_REQUEST),
    ;

    private final String code;
    private final String message;
    private final int status;
}
