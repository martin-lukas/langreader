package org.lukas.langreader.exception;

public class DuplicateExpressionException extends RuntimeException {
    public DuplicateExpressionException() {
        super();
    }

    public DuplicateExpressionException(String message) {
        super(message);
    }

    public DuplicateExpressionException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicateExpressionException(Throwable cause) {
        super(cause);
    }
}
