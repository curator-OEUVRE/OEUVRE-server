package com.curator.oeuvre.dto.floor.response;

import com.curator.oeuvre.domain.Floor;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@ApiModel(value = "🎞 플로어 생성 API Response")
public class PostFloorResponseDto {

    @ApiModelProperty(notes = "플로어 no", example = "1")
    private final Long floorNo;

    public PostFloorResponseDto(Floor floor) {
        this.floorNo = floor.getNo();
    }

}