package com.nocommittoday.module.exception.handler.servlet;


import com.nocommittoday.module.exception.core.AbstractDomainException;
import com.nocommittoday.module.exception.core.ErrorCodeType;
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
public class ServletExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(ServletExceptionHandler.class);


    @ExceptionHandler(AbstractDomainException.class)
    private ResponseEntity<Object> handleDomainException(
            final AbstractDomainException ex, final WebRequest request) {
        final HttpHeaders headers = new HttpHeaders();
        final ErrorCodeType errorCode = ex.getErrorCode();
        final HttpStatusCode status = HttpStatusCode.valueOf(errorCode.getStatus());
        final ProblemDetail body = createProblemDetail(
                ex, status, errorCode.getMessage(), null, null, request);
        body.setProperty("errorCode", errorCode.getCode());
        return handleExceptionInternal(ex, body, headers, status, request);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleRootException(
            final Exception ex, final WebRequest request) {
        final HttpHeaders headers = new HttpHeaders();
        final HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        final ProblemDetail body = createProblemDetail(
                ex, status, "서버에 문제가 발생했습니다.", null, null, request);
        return handleExceptionInternal(ex, body, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            final MethodArgumentNotValidException ex,
            final HttpHeaders headers,
            final HttpStatusCode status,
            final WebRequest request
    ) {
        final ProblemDetail body = ex.updateAndGetBody(getMessageSource(), LocaleContextHolder.getLocale());
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
            final Exception ex,
            final Object body,
            final HttpHeaders headers,
            final HttpStatusCode statusCode,
            final WebRequest request
    ) {
        log.error("handleExceptionInternal", ex);
        return super.handleExceptionInternal(ex, body, headers, statusCode, request);
    }
}
