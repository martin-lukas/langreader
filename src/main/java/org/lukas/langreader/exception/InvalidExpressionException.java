package org.lukas.langreader.exception;

public class InvalidExpressionException extends RuntimeException {
    public InvalidExpressionException() {
        super();
    }

    public InvalidExpressionException(String message) {
        super(message);
    }

    public InvalidExpressionException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidExpressionException(Throwable cause) {
        super(cause);
    }
}
