package com.mx.development.jose.said.internal.common.exception;

/** The request is valid but conflicts with the current state of the resource (HTTP 409). */
public class ConflictException extends TallerException {
    public ConflictException(String errorCode, String description) {
        super(errorCode, description);
    }
}
