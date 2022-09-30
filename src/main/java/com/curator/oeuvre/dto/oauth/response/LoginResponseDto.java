package com.curator.oeuvre.dto.oauth.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@ToString
@EqualsAndHashCode
public class LoginResponseDto {

    @ApiModelProperty(example = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ~~~~")
    private final String accessToken;

    @ApiModelProperty(example = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ~~~~")
    private final String refreshToken;

    public LoginResponseDto(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

}
