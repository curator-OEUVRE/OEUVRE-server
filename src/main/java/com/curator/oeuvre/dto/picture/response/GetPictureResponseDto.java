package com.curator.oeuvre.dto.picture.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@ApiModel(value = "🌃 사진 조회 API Response")
public class GetPictureResponseDto {

    @ApiModelProperty(notes = "사진 no", example = "1")
    private final Long pictureNo;

    @ApiModelProperty(notes = "플로어 no", example = "1")
    private final Long floorNo;

    @ApiModelProperty(notes = "이미지 url", example = "image_url")
    private final String imageUrl;

    @ApiModelProperty(notes = "사진 설명", example = "노을을 보면서 한컷")
    private final String description;

    @ApiModelProperty(notes = "본인 사진 여부", example = "true")
    private final Boolean isMine;

    @ApiModelProperty(notes = "좋아요 여부", example = "true")
    private final Boolean isLiked;

    @ApiModelProperty(notes = "스크랩 여부", example = "true")
    private final Boolean isScraped;

    public GetPictureResponseDto (
            Long pictureNo,
            Long floorNo,
            String imageUrl,
            String description,
            Boolean isMine,
            Boolean isLiked,
            Boolean isScraped
    ) {
        this.pictureNo = pictureNo;
        this.floorNo = floorNo;
        this.imageUrl = imageUrl;
        this.description = description;
        this.isMine = isMine;
        this.isLiked = isLiked;
        this.isScraped = isScraped;
    }
}
