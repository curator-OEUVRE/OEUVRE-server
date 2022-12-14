package com.curator.oeuvre.dto.user.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@ApiModel(value = "π€ νμκ°μ API Response")
public class SignUpResponseDto {

    @ApiModelProperty(notes = "νμ no", example = "1")
    private final Long userNo;

    @ApiModelProperty(notes = "μ΄μΈμ€ ν ν°", example = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ~~~~")
    private final String accessToken;

    @ApiModelProperty(notes = "λ¦¬νλ μ ν ν°", example = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ~~~~")
    private final String refreshToken;

    public SignUpResponseDto(Long userNo, String accessToken, String refreshToken) {
        this.userNo = userNo;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

}

