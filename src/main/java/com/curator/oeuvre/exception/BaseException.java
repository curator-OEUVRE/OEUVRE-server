package com.curator.oeuvre.exception;

import com.curator.oeuvre.constant.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Map;

/** 비즈니스 로직 예외 처리 통합 관리용 부모 객체입니다 */
@Getter
public class BaseException extends RuntimeException {

    ErrorCode errorCode;
    String responseMessage;
    HttpStatus httpStatus;
    Map<String, String> data;


    public BaseException(ErrorCode errorCode) {
        super();
        this.errorCode = errorCode;
        this.responseMessage = errorCode.getMessage();
        this.httpStatus = errorCode.getHttpStatus();
    }

    public BaseException(ErrorCode errorCode, Map<String, String> data) {
        super();
        this.errorCode = errorCode;
        this.responseMessage = errorCode.getMessage();
        this.httpStatus = errorCode.getHttpStatus();
        this.data = data;
    }
}