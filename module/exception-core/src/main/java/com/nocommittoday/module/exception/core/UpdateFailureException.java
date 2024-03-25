package com.nocommittoday.module.exception.core;

public class UpdateFailureException extends AbstractDomainException {

    public UpdateFailureException(final Throwable cause) {
        super(CommonErrorCode.UPDATE_FAILURE, cause);
    }
}
