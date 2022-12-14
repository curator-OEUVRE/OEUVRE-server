package com.curator.oeuvre.dto.oauth.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@ToString
@EqualsAndHashCode
@ApiModel(value = "๐ ๋ก๊ทธ์ธ API Response")
public class LoginResponseDto {

    @ApiModelProperty(notes = "์ด์ธ์คํ ํฐ", example = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ~~~~")
    private final String accessToken;

    @ApiModelProperty(notes = "๋ฆฌํ๋ ์ ํ ํฐ", example = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ~~~~")
    private final String refreshToken;

    public LoginResponseDto(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

}
