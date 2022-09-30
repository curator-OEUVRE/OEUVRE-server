package com.curator.oeuvre.exception;

import com.curator.oeuvre.constant.ErrorCode;
import lombok.Getter;

@Getter
public class UnauthorizedException extends BaseException {
    private String message;

    public UnauthorizedException(String message) {
        super(ErrorCode._BAD_REQUEST);
        this.message = message;
    }

    public UnauthorizedException(ErrorCode errorCode, String message) {
        super(errorCode);
        this.message = message;
    }

    public UnauthorizedException(ErrorCode errorCode) {
        super(errorCode);
    }
}
