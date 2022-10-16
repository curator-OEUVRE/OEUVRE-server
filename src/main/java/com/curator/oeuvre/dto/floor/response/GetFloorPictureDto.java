package com.curator.oeuvre.dto.floor.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import java.util.List;

@Getter
@ToString
@EqualsAndHashCode
@Builder
public class GetFloorPictureDto {

    @ApiModelProperty(notes = "사진 no", example = "1")
    private final Long pictureNo;

    @ApiModelProperty(notes = "사진 순서", example = "1")
    private final Integer queue;

    @ApiModelProperty(notes = "이미지 url", example = "image_url")
    private final String imageUrl;

    @ApiModelProperty(notes = "사진 설명", example = "노을을 보면서 한컷")
    private final String description;

    @ApiModelProperty(notes = "세로 길이", example = "188")
    private final Float height;

    @ApiModelProperty(notes = "세로 길이", example = "70")
    private final Float width;

    @ApiModelProperty(notes = "y축 위치", example = "93")
    private final Float location;

    @ApiModelProperty(notes = "해시태그 목록", example = "[\"#노을\", \"#바다\", \"#제주\"]")
    private final List<String> hashtags;

    public GetFloorPictureDto (
            Long pictureNo,
            Integer queue,
            String imageUrl,
            String description,
            Float height,
            Float width,
            Float location,
            List<String> hashtags
            ) {
        this.pictureNo = pictureNo;
        this.queue = queue;
        this.imageUrl = imageUrl;
        this.description = description;
        this.height = height;
        this.width = width;
        this.location = location;
        this.hashtags = hashtags;
    }
}
