package com.procrastination.exception.user;

import com.procrastination.exception.ClientErrorException;
import org.springframework.http.HttpStatus;

public class UserAuthenticationException extends ClientErrorException {

    public UserAuthenticationException() {
        super(HttpStatus.BAD_REQUEST, "이메일이나 비밀번호가 일치하지 않습니다.");
    }
}
