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
    _INTERNAL_SERVER_ERROR(INTERNAL_SERVER_ERROR, "C000", "서버 에러, 관리자에게 문의 바랍니다."),
    _BAD_REQUEST(BAD_REQUEST, "C001", "잘못된 요청입니다."),
    _UNAUTHORIZED(UNAUTHORIZED, "C002", "권한이 없습니다."),

    _METHOD_NOT_ALLOWED(METHOD_NOT_ALLOWED, "C003", "지원하지 않는 Http Method 입니다."),


    /* Auth 관련 오류 */
    EXPIRED_TOKEN(BAD_REQUEST, "AUTH001", "만료된 엑세스 토큰입니다."),
    INVALID_REFRESH_TOKEN(BAD_REQUEST, "AUTH002", "리프레시 토큰이 유효하지 않습니다."),
    MISMATCH_REFRESH_TOKEN(BAD_REQUEST, "AUTH003", "리프레시 토큰의 유저 정보가 일치하지 않습니다."),
    INVALID_AUTH_TOKEN(UNAUTHORIZED, "AUTH004", "유효하지 않은 토큰입니다."),
    UNAUTHORIZED_USER(UNAUTHORIZED, "AUTH005", "현재 내 계정 정보가 존재하지 않습니다."),
    REFRESH_TOKEN_NOT_FOUND(NOT_FOUND, "AUTH006", "로그아웃 된 사용자입니다."),
    FORBIDDEN_USER(FORBIDDEN, "AUTH007", "권한이 없는 유저입니다."),
    LOGIN_FAILED(UNAUTHORIZED, "AUTH008", "로그인에 실패했습니다."),


    /* OAuth 관련 오류 */
    KAKAO_BAD_REQUEST(BAD_REQUEST, "OAUTH001", "유효하지 않은 카카오 토큰입니다."),
    KAKAO_USER_NOT_FOUND(BAD_REQUEST, "OAUTH002", "카카오 유저를 찾을 수 없습니다."),
    KAKAO_USER_EMAIL_NOT_FOUND(BAD_REQUEST, "OAUTH003", "이메일 동의를 하지 않았습니다."),
    GOOGLE_BAD_REQUEST(BAD_REQUEST, "OAUTH004", "유효하지 않은 구글 토큰입니다."),
    APPLE_BAD_REQUEST(BAD_REQUEST, "OAUTH005", "유효하지 않은 애플 토큰입니다."),
    APPLE_SERVER_ERROR(FORBIDDEN, "OAUTH006", "애플 서버와 통신에 실패하였습니다."),
    FAIL_TO_MAKE_APPLE_PUBLIC_KEY(BAD_REQUEST, "OAUTH007", "새로운 애플 공개키 생성에 실패하였습니다."),




    /* User 관련 오류 */
    CANNOT_FOLLOW_MYSELF(BAD_REQUEST, "U001", "자기 자신은 팔로우 할 수 없습니다."),
    USER_ALREADY_EXIST(BAD_REQUEST, "U002", "이미 가입된 유저입니다."),
    USER_NOT_FOUND(NOT_FOUND, "U003", "가입되지 않은 유저입니다."),
    DUPLICATED_ID(BAD_REQUEST, "U004", "중복된 ID 입니다."),




    /* Floor 관련 오류 */
    FLOOR_NOT_FOUND(NOT_FOUND, "F001", "존재하지 않는 플로어 입니다."),
    FORBIDDEN_FLOOR(FORBIDDEN, "F002", "플로어 접근 권한이 없습니다."),



    /* Picture 관련 오류 */
    EMPTY_IMAGE_URL(BAD_REQUEST, "P001", "image_url은 null이 될 수 없습니다."),
    EMPTY_QUEUE(BAD_REQUEST, "P002", "queue는 null이 될 수 없습니다."),
    EMPTY_HEIGHT(BAD_REQUEST, "P003", "height는 null이 될 수 없습니다."),
    EMPTY_LOCATION(BAD_REQUEST, "P004", "location은 null이 될 수 없습니다."),
    PICTURE_NOT_FOUND(NOT_FOUND, "P005", "존재하지 않는 사진 입니다."),
    ALREADY_LIKED(BAD_REQUEST, "P006", "이미 좋아요한 사진입니다."),
    LIKE_NOT_FOUND(BAD_REQUEST, "P007", "좋아요 한 적 없는 사진입니다."),
    ALREADY_SCRAPED(BAD_REQUEST, "P008", "이미 스크랩한 사진입니다."),
    SCRAP_NOT_FOUND(BAD_REQUEST, "P009", "스크랩 한 적 없는 사진입니다."),
    FORBIDDEN_PICTURE(FORBIDDEN, "P010", "사진 접근 권한이 없습니다."),
    EMPTY_PICTURE_NO(BAD_REQUEST, "P011", "사진 no는 null이 될 수 없습니다."),



    /* Database 관련 오류 */
    DUPLICATE_RESOURCE(CONFLICT, "D001", "데이터가 이미 존재합니다."),


    ;

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
