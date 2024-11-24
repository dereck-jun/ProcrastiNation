package com.procrastination.exception.jwt;

import com.procrastination.exception.ClientErrorException;
import org.springframework.http.HttpStatus;

public class JwtProcessingException extends ClientErrorException {

    public JwtProcessingException(String msg) {
        super(HttpStatus.BAD_REQUEST, msg);
    }
}
