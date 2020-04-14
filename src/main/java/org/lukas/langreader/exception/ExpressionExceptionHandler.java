package org.lukas.langreader.exception;

import org.lukas.langreader.rest.ExpressionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExpressionExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<ExpressionResponse> handleException(Exception exc) {
        ExpressionResponse error = new ExpressionResponse();

        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setMessage(exc.getMessage());
        error.setTimeStamp(System.currentTimeMillis());

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
