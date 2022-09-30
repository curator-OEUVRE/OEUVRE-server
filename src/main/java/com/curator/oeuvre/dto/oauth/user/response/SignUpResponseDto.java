package com.curator.oeuvre.dto.oauth.user.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
public class SignUpResponseDto {

    @ApiModelProperty(example = "1")
    private final Long userNo;

    @ApiModelProperty(example = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ~~~~")
    private final String accessToken;

    @ApiModelProperty(example = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ~~~~")
    private final String refreshToken;

    public SignUpResponseDto(Long userNo, String accessToken, String refreshToken) {
        this.userNo = userNo;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

}

