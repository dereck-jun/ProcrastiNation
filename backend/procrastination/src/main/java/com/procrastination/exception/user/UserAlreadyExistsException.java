package com.procrastination.exception.user;

import com.procrastination.exception.ClientErrorException;
import org.springframework.http.HttpStatus;

public class UserAlreadyExistsException extends ClientErrorException {

    public UserAlreadyExistsException() {
        super(HttpStatus.CONFLICT, "User already exists");
    }

    public UserAlreadyExistsException(String email) {
        super(HttpStatus.CONFLICT, email + " 에 해당하는 사용자가 이미 존재합니다.");
    }
}
