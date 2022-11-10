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
@ApiModel(value = "📖 방명록 이동 플로어 전체 조회 API Response")
public class GetFloorToMoveResponseDto {

    @ApiModelProperty(notes = "플로어 no", example = "1")
    private final Long floorNo;

    @ApiModelProperty(notes = "플로어 순서", example = "1")
    private final Integer queue;

    @ApiModelProperty(notes = "플로어 이름", example = "제주 여행 기록")
    private final String name;

    public GetFloorToMoveResponseDto(Floor floor) {
        this.floorNo = floor.getNo();
        this.queue = floor.getQueue();
        this.name = floor.getName();
    }

}
