package com.curator.oeuvre.exception;

import com.curator.oeuvre.constant.ErrorCode;
import lombok.Getter;

@Getter
public class InternalServerException extends BaseException {
    private String message;

    public InternalServerException(String message) {
        super(ErrorCode._BAD_REQUEST);
        this.message = message;
    }

    public InternalServerException(ErrorCode errorCode, String message) {
        super(errorCode);
        this.message = message;
    }

    public InternalServerException(ErrorCode errorCode) {
        super(errorCode);
    }
}
