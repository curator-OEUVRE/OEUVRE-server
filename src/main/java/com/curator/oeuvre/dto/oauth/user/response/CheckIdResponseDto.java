package com.curator.oeuvre.dto.oauth.user.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
public class CheckIdResponseDto {

    @ApiModelProperty(example = "true")
    private final Boolean isPossible;

    public CheckIdResponseDto(Boolean isPossible) {
        this.isPossible = isPossible;
    }
}
