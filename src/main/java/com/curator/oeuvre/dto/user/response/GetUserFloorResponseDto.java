package com.curator.oeuvre.dto.user.response;

import com.curator.oeuvre.domain.Floor;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import java.util.List;

@Getter
@ToString
@EqualsAndHashCode
@ApiModel(value = "👤 유저 플로어 전체 조회 API Response")
public class GetUserFloorResponseDto {

    @ApiModelProperty(notes = "플로어 순서", example = "1")
    private final Integer queue;

    @ApiModelProperty(notes = "플로어 no", example = "1")
    private final Long floorNo;

    @ApiModelProperty(notes = "플로어 이름", example = "제주 여행 기록")
    private final String name;

    @ApiModelProperty(notes = "배경 색상코드", example = "#FFFFFF")
    private final String color;

    @ApiModelProperty(notes = "배경 그라데이션", example = "FULL")
    private final String gradient;

    @ApiModelProperty(notes = "배경 질감", example = "0")
    private final Integer texture;

    @ApiModelProperty(notes = "썸네일 목록")
    private final List<GetUserFloorThumbnailDto> thumbnails;


    public GetUserFloorResponseDto(Floor floor, List<GetUserFloorThumbnailDto> thumbnails) {
        this.queue = floor.getQueue();
        this.floorNo = floor.getNo();
        this.name = floor.getName();
        this.color = floor.getColor();
        this.gradient = floor.getGradient();
        this.texture = floor.getTexture();
        this.thumbnails = thumbnails;
    }
}

