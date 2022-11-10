package com.curator.oeuvre.dto.user.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@ApiModel(value = "👤 아이디 중복 검사 API Response")

public class CheckIdResponseDto {

    @ApiModelProperty(notes = "사용 가능 여부", example = "true")
    private final Boolean isPossible;

    public CheckIdResponseDto(Boolean isPossible) {
        this.isPossible = isPossible;
    }
}
