package com.curator.oeuvre.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

/** 에러 코드를 관리하기 위한 Enum 입니다 */
@Getter
@AllArgsConstructor
public enum ErrorCode {

    /* 공통 오류 */
    _INTERNAL_SERVER_ERROR(INTERNAL_SERVER_ERROR, "C000", "서버 에러, 관리자에게 문의 바랍니다"),
    _BAD_REQUEST(BAD_REQUEST, "C001", "잘못된 요청입니다"),
    _UNAUTHORIZED(UNAUTHORIZED, "C002", "권한이 없습니다"),

    _METHOD_NOT_ALLOWED(METHOD_NOT_ALLOWED, "C003", "지원하지 않는 Http Method 입니다"),


    /* Auth 관련 오류 */
    EXPIRED_TOKEN(BAD_REQUEST, "AUTH001", "만료된 엑세스 토큰입니다"),
    INVALID_REFRESH_TOKEN(BAD_REQUEST, "AUTH002", "리프레시 토큰이 유효하지 않습니다"),
    MISMATCH_REFRESH_TOKEN(BAD_REQUEST, "AUTH003", "리프레시 토큰의 유저 정보가 일치하지 않습니다"),
    INVALID_AUTH_TOKEN(UNAUTHORIZED, "AUTH004", "권한 정보가 없는 토큰입니다"),
    UNAUTHORIZED_USER(UNAUTHORIZED, "AUTH005", "현재 내 계정 정보가 존재하지 않습니다"),
    REFRESH_TOKEN_NOT_FOUND(NOT_FOUND, "AUTH006", "로그아웃 된 사용자입니다"),
    FORBIDDEN_USER(FORBIDDEN, "AUTH007", "권한이 없는 유저입니다"),
    LOGIN_FAILED(UNAUTHORIZED, "AUTH008", "로그인에 실패했습니다"),

    /* OAuth 관련 오류 */
    KAKAO_BAD_REQUEST(BAD_REQUEST, "OAUTH001", "카카오 토큰 오류"),
    KAKAO_USER_NOT_FOUND(BAD_REQUEST, "OAUTH002", "카카오 유저를 찾을 수 없습니다"),
    KAKAO_USER_EMAIL_NOT_FOUND(BAD_REQUEST, "OAUTH003", "이메일 동의를 하지 않았습니다"),


    /* User 관련 오류 */
    CANNOT_FOLLOW_MYSELF(BAD_REQUEST, "U001", "자기 자신은 팔로우 할 수 없습니다"),
    USER_ALREADY_EXIST(BAD_REQUEST, "U002", "이미 가입된 유저입니다"),
    USER_NOT_FOUND(NOT_FOUND, "U003", "가입되지 않은 유저입니다."),


    /* Database 관련 오류 */
    DUPLICATE_RESOURCE(CONFLICT, "D001", "데이터가 이미 존재합니다"),


    ;

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
