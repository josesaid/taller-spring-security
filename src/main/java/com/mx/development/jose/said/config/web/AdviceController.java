package com.mx.development.jose.said.config.web;

import com.mx.development.jose.said.internal.common.exception.BadRequestException;
import com.mx.development.jose.said.internal.common.exception.ConflictException;
import com.mx.development.jose.said.internal.common.exception.NotFoundException;
import com.mx.development.jose.said.internal.common.exception.TallerException;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

import java.util.logging.Level;

@Log
@ControllerAdvice
public class AdviceController {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ProblemDetail> generalError(Exception e) {
        // Never leak internal messages (SQL, stack details, etc.) to the client: log them and answer generic
        log.log(Level.SEVERE, "Unexpected error", e);
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        ProblemDetail response = ProblemDetail.forStatusAndDetail(status, "Unexpected error");
        return new ResponseEntity<>(response, status);
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseStatusException responseStatusException(ResponseStatusException e) {
        return e;
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ProblemDetail> notFoundExceptionHandler(TallerException e) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        ProblemDetail response = ProblemDetail.forStatusAndDetail(status, e.getDescription());
        response.setTitle(e.getErrorCode());
        e.printStackTrace();
        return new ResponseEntity<>(response, status);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ProblemDetail> badRequestExceptionHandler(TallerException e) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ProblemDetail response = ProblemDetail.forStatusAndDetail(status, e.getMessage());
        response.setTitle(e.getErrorCode());
        e.printStackTrace();
        return new ResponseEntity<>(response, status);
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ProblemDetail> conflictExceptionHandler(TallerException e) {
        HttpStatus status = HttpStatus.CONFLICT;
        ProblemDetail response = ProblemDetail.forStatusAndDetail(status, e.getDescription());
        response.setTitle(e.getErrorCode());
        return new ResponseEntity<>(response, status);
    }

}
