package com.jspapps.consumptionapp.application.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class CustomRuntimeException extends RuntimeException {

    private String message;
    private String messageKey;
    private HttpStatus status;
    private Exception exception;

    public CustomRuntimeException(String message) {
        super(message);
        this.message = message;
    }

    public CustomRuntimeException(String message, String messageKey) {
        super(message);
        this.message = message;
        this.messageKey = messageKey;
    }

    public CustomRuntimeException(String message, Exception exception) {
        super(message);
        this.message = message;
        this.exception = exception;
    }

    public CustomRuntimeException(String message, HttpStatus status, Exception exception) {
        super(message);
        this.message = message;
        this.status = status;
        this.exception = exception;
    }
}
