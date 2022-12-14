package com.curator.oeuvre.dto.comment.response;

import com.curator.oeuvre.domain.Floor;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@ApiModel(value = "π λ°©λͺλ‘ μ΄λ νλ‘μ΄ μ μ²΄ μ‘°ν API Response")
public class GetFloorToMoveResponseDto {

    @ApiModelProperty(notes = "νλ‘μ΄ no", example = "1")
    private final Long floorNo;

    @ApiModelProperty(notes = "νλ‘μ΄ μμ", example = "1")
    private final Integer queue;

    @ApiModelProperty(notes = "νλ‘μ΄ μ΄λ¦", example = "μ μ£Ό μ¬ν κΈ°λ‘")
    private final String name;

    public GetFloorToMoveResponseDto(Floor floor) {
        this.floorNo = floor.getNo();
        this.queue = floor.getQueue();
        this.name = floor.getName();
    }

}
