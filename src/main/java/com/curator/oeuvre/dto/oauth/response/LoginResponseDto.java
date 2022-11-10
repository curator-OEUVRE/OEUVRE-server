package com.curator.oeuvre.dto.oauth.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@ToString
@EqualsAndHashCode
@ApiModel(value = "🔑 로그인 API Response")
public class LoginResponseDto {

    @ApiModelProperty(notes = "어세스토큰", example = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ~~~~")
    private final String accessToken;

    @ApiModelProperty(notes = "리프레시 토큰", example = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ~~~~")
    private final String refreshToken;

    public LoginResponseDto(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

}
