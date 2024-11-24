package com.procrastination.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ClientErrorException extends RuntimeException {
    private final HttpStatus httpStatus;

    public ClientErrorException(HttpStatus httpStatus, String msg) {
        super(msg);
        this.httpStatus = httpStatus;
    }
}
