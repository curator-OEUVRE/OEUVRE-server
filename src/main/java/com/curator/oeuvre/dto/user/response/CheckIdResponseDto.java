package com.curator.oeuvre.dto.user.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@ApiModel(value = "π€ μμ΄λ μ€λ³΅ κ²μ¬ API Response")

public class CheckIdResponseDto {

    @ApiModelProperty(notes = "μ¬μ© κ°λ₯ μ¬λΆ", example = "true")
    private final Boolean isPossible;

    public CheckIdResponseDto(Boolean isPossible) {
        this.isPossible = isPossible;
    }
}
