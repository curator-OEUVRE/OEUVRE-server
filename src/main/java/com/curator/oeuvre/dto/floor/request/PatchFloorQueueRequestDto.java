package com.curator.oeuvre.dto.floor.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel(value = "π νλ‘μ΄ μμ μμ  API Request")
public class PatchFloorQueueRequestDto {

    @ApiModelProperty(notes = "νλ‘μ΄ no", example = "1")
    @NotNull(message = "νλ‘μ΄ noλ₯Ό μλ ₯ν΄μ£ΌμΈμ")
    private Long floorNo;

    @ApiModelProperty(notes = "νλ‘μ΄ μμ", example = "1")
    @NotNull(message = "νλ‘μ΄ μμλ₯Ό μλ ₯ν΄μ£ΌμΈμ")
    private Integer queue;

}

