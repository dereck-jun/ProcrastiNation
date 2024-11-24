package com.procrastination.exception.jwt;

import com.procrastination.exception.ClientErrorException;
import org.springframework.http.HttpStatus;

public class InvalidJwtTokenException extends ClientErrorException {
    public InvalidJwtTokenException() {
        super(HttpStatus.BAD_REQUEST, "유효하지 않는 토큰입니다.");
    }

    public InvalidJwtTokenException(String msg) {
        super(HttpStatus.BAD_REQUEST, msg);
    }
}
