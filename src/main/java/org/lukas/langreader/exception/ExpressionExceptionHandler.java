package org.lukas.langreader.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExpressionExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<ExpressionErrorResponse> handleException(DuplicateExpressionException exc) {
        ExpressionErrorResponse error = new ExpressionErrorResponse();

        error.setStatus(HttpStatus.CONFLICT.value());
        error.setMessage(exc.getMessage());
        error.setTimeStamp(System.currentTimeMillis());

        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler
    public ResponseEntity<ExpressionErrorResponse> handleException(InvalidExpressionException exc) {
        ExpressionErrorResponse error = new ExpressionErrorResponse();

        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setMessage(exc.getMessage());
        error.setTimeStamp(System.currentTimeMillis());

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ExpressionErrorResponse> handleException(Exception exc) {
        ExpressionErrorResponse error = new ExpressionErrorResponse();

        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setMessage(exc.getMessage());
        error.setTimeStamp(System.currentTimeMillis());

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
