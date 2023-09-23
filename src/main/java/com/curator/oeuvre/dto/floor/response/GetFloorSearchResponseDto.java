package com.curator.oeuvre.dto.floor.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@ApiModel(value = "🎞 플로어 검색 API Response")
public class GetFloorSearchResponseDto {

    @ApiModelProperty(notes = "플로어 no", example = "1")
    private final Long floorNo;

    @ApiModelProperty(notes = "플로어 이름", example = "제주 여행 기록")
    private final String floorName;

    @ApiModelProperty(notes = "전시회 이름", example = "원진이의 전시회")
    private final String exhibitionName;

    @ApiModelProperty(notes = "썸네일 사진 url", example = "image_url")
    private final String thumbnailUrl;

    @ApiModelProperty(notes = "작은 썸네일 사진 url", example = "image_url")
    private final String smallThumbnailUrl;

    @ApiModelProperty(notes = "세로 길이", example = "188")
    private final Float height;

    @ApiModelProperty(notes = "가로 길이", example = "70")
    private final Float width;

    public GetFloorSearchResponseDto(
            Long floorNo,
            String floorName,
            String exhibitionName,
            String thumbnailUrl,
            String smallThumbnailUrl,
            Float height,
            Float width
    ) {
        this.floorNo = floorNo;
        this.floorName = floorName;
        this.exhibitionName = exhibitionName;
        this.thumbnailUrl = thumbnailUrl;
        this.smallThumbnailUrl = smallThumbnailUrl;
        this.height = height;
        this.width = width;
    }
}