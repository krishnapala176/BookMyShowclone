package com.bookmyshow.exception.Email;

public class EmailSendFailureException extends RuntimeException {
    public EmailSendFailureException(String message) {
        super(message);
    }

    public EmailSendFailureException(String message, Throwable cause) {
        super(message, cause);
    }
}
