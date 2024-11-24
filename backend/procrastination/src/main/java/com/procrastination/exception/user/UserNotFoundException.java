package com.procrastination.exception.user;

import com.procrastination.exception.ClientErrorException;
import org.springframework.http.HttpStatus;

public class UserNotFoundException extends ClientErrorException {

    public UserNotFoundException() {
        super(HttpStatus.NOT_FOUND, "해당 사용자를 찾을 수 없습니다.");
    }

    public UserNotFoundException(String email) {
        super(HttpStatus.NOT_FOUND, "입력하신 " + email + "에 해당하는 사용자가 없습니다.");
    }
}
