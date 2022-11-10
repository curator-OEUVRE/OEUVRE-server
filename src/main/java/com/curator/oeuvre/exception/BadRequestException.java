package com.curator.oeuvre.exception;

import com.curator.oeuvre.constant.ErrorCode;
import lombok.Getter;

/** Status: 400
 * Bad Request Error 반환하는 커스텀 클래스입니다 */
@Getter
public class BadRequestException extends BaseException {
    private String message;

    public BadRequestException(String message) {
        super(ErrorCode._BAD_REQUEST);
        this.message = message;
    }

    public BadRequestException(ErrorCode errorCode, String message) {
        super(errorCode);
        this.message = message;
    }

    public BadRequestException(ErrorCode errorCode) {
        super(errorCode);
    }
}
