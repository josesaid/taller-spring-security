package com.mx.development.jose.said.internal.common.exception;

import lombok.Getter;

@Getter
public class TallerException extends RuntimeException {
    private String description;
    private String errorCode;

    public TallerException(String description) {
        super(description);
        this.description = description;
    }

    public TallerException(String errorCode, String description) {
        super(description);
        this.description = description;
        this.errorCode = errorCode;
    }

    public TallerException(String message, Throwable cause) {
        super(message, cause);
    }
}
