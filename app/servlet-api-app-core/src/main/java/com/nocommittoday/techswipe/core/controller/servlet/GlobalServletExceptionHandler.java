package com.nocommittoday.techswipe.core.controller.servlet;


import com.nocommittoday.techswipe.domain.core.AbstractDomainException;
import com.nocommittoday.techswipe.domain.core.ErrorCodeType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalServletExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalServletExceptionHandler.class);

    @ExceptionHandler(AbstractDomainException.class)
    private ResponseEntity<Object> handleDomainException(
            AbstractDomainException ex, WebRequest request) {
        HttpHeaders headers = new HttpHeaders();
        ErrorCodeType errorCode = ex.getErrorCode();
        HttpStatusCode status = HttpStatusCode.valueOf(errorCode.getStatus());
        ProblemDetail body = createProblemDetail(
                ex, status, errorCode.getMessage(), null, null, request);
        body.setProperty("errorCode", errorCode.getCode());

        if (HttpStatus.valueOf(errorCode.getStatus()).is5xxServerError()) {
            log.error("handleDomainException[{}]: {}", ex.getClass(), ex.getMessage(), ex);
        } else {
            log.info("handleDomainException[{}]: {}", ex.getClass(), ex.getMessage(), ex);
        }

        return handleExceptionInternal(ex, body, headers, status, request);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleRootException(Exception ex, WebRequest request) {
        HttpHeaders headers = new HttpHeaders();
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        ProblemDetail body = createProblemDetail(
                ex, status, "서버에 문제가 발생했습니다.", null, null, request);
        log.error("handleRootException[{}]: {}", ex.getClass(), ex.getMessage(), ex);
        return handleExceptionInternal(ex, body, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request
    ) {
        ProblemDetail body = ex.updateAndGetBody(getMessageSource(), LocaleContextHolder.getLocale());
        body.setProperty("fields", ex.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> new FieldErrorResponse(
                        fieldError.getField(),
                        fieldError.getRejectedValue(),
                        fieldError.getDefaultMessage()
                )).toList()
        );
        return handleExceptionInternal(ex, body, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(
            Exception ex, Object body, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request
    ) {
        log.debug("handleExceptionInternal[{}]: {}", ex.getClass(), ex.getMessage(), ex);
        return super.handleExceptionInternal(ex, body, headers, statusCode, request);
    }
}
