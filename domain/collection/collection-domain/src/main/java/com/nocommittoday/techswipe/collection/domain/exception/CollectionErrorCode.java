package com.nocommittoday.techswipe.collection.domain.exception;

import com.nocommittoday.techswipe.core.domain.ErrorCodeType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.net.HttpURLConnection;

@Getter
@RequiredArgsConstructor
public enum CollectionErrorCode implements ErrorCodeType {
    CATEGORIZE_UNABLE("COLLECTION-001", "카테고리화 할 수 없습니다.", HttpURLConnection.HTTP_BAD_REQUEST),
    SUMMARIZE_UNABLE("COLLECTION-002", "요약할 수 없습니다.", HttpURLConnection.HTTP_BAD_REQUEST),
    NOT_FOUND("COLLECTION-003", "수집된 컨텐츠를 찾을 수 없습니다.", HttpURLConnection.HTTP_NOT_FOUND),
    PUBLISH_UNABLE("COLLECTION-006", "컨텐츠를 발행할 수 없습니다.", HttpURLConnection.HTTP_BAD_REQUEST),
    CATEGORY_NOT_EDITABLE("COLLECTION-007", "카테고리를 수정할 수 없습니다.", HttpURLConnection.HTTP_BAD_REQUEST),
    CATEGORY_NOT_APPLICABLE("COLLECTION-008", "카테고리를 컨텐츠에 반영할 수 없습니다.", HttpURLConnection.HTTP_BAD_REQUEST),
    SUMMARY_FORMAT_NOT_REGISTRABLE("COLLECTION-009", "등록할 수 없는 요약 형식입니다.", HttpURLConnection.HTTP_BAD_REQUEST),
    ILLEGAL_PROVIDER_ID("COLLECTION-010", "잘못된 컨텐츠 제공자 아이디입니다.", HttpURLConnection.HTTP_BAD_REQUEST),
    ALREADY_COLLECTED("COLLECTION-011", "이미 수집된 컨텐츠입니다.", HttpURLConnection.HTTP_BAD_REQUEST),
    ;

    private final String code;
    private final String message;
    private final int status;
}
