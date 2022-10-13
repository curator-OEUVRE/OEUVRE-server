package com.curator.oeuvre.dto.user.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@ApiModel(value = "👤 회원가입 API Response")
public class SignUpResponseDto {

    @ApiModelProperty(notes = "회원 no", example = "1")
    private final Long userNo;

    @ApiModelProperty(notes = "어세스 토큰", example = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ~~~~")
    private final String accessToken;

    @ApiModelProperty(notes = "리프레시 토큰", example = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ~~~~")
    private final String refreshToken;

    public SignUpResponseDto(Long userNo, String accessToken, String refreshToken) {
        this.userNo = userNo;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

}

