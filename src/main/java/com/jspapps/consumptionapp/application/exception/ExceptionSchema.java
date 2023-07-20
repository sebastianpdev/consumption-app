package com.jspapps.consumptionapp.application.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@NoArgsConstructor
public class ExceptionSchema {

    private String message;
    private String messageKey = HttpStatus.INTERNAL_SERVER_ERROR.toString();
    private String stackTrace;

    public ExceptionSchema(String message) {
        this.message = message;
    }

    public ExceptionSchema(String message, String messageKey) {
        this.message = message;
        this.messageKey = messageKey;
    }
}
