package com.curator.oeuvre.exception;

import com.curator.oeuvre.constant.ErrorCode;
import lombok.Getter;

@Getter
public class ForbiddenException extends BaseException {
    private String message;

    public ForbiddenException(String message) {
        super(ErrorCode._BAD_REQUEST);
        this.message = message;
    }

    public ForbiddenException(ErrorCode errorCode, String message) {
        super(errorCode);
        this.message = message;
    }

    public ForbiddenException(ErrorCode errorCode) {
        super(errorCode);
    }
}
