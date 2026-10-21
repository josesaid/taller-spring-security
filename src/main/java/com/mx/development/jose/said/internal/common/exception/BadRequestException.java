package com.mx.development.jose.said.internal.common.exception;

public class BadRequestException extends TallerException {
    public BadRequestException(String errorCode, String message) {
        super(errorCode, message);
    }
}
