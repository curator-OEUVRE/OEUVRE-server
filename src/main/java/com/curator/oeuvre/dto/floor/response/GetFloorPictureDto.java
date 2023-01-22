package com.curator.oeuvre.dto.floor.response;

import com.curator.oeuvre.domain.Picture;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import java.util.List;

@Getter
@ToString
@EqualsAndHashCode
public class GetFloorPictureDto {

    @ApiModelProperty(notes = "사진 순서", example = "1")
    private final Integer queue;

    @ApiModelProperty(notes = "사진 no", example = "1")
    private final Long pictureNo;

    @ApiModelProperty(notes = "이미지 url", example = "image_url")
    private final String imageUrl;

    @ApiModelProperty(notes = "작품 제목", example = "노을")
    private final String title;

    @ApiModelProperty(notes = "제작년도", example = "2022")
    private final String manufactureYear;

    @ApiModelProperty(notes = "작품 재료", example = "캔버스에 유채")
    private final String material;

    @ApiModelProperty(notes = "작품 크기", example = "22*80(cm)")
    private final String scale;

    @ApiModelProperty(notes = "작품 설명", example = "노을을 보면서 한컷")
    private final String description;

    @ApiModelProperty(notes = "세로 길이", example = "188")
    private final Float height;

    @ApiModelProperty(notes = "가로 길이", example = "70")
    private final Float width;

    @ApiModelProperty(notes = "y축 위치", example = "93")
    private final Float location;

    @ApiModelProperty(notes = "해시태그 목록", example = "[\"노을\", \"바다\", \"제주\"]")
    private final List<String> hashtags;

    public GetFloorPictureDto (
            Picture picture,
            List<String> hashtags
            ) {
        this.queue = picture.getQueue();
        this.pictureNo = picture.getNo();
        this.imageUrl = picture.getImageUrl();
        this.title = picture.getTitle();
        this.manufactureYear = picture.getManufactureYear();
        this.material = picture.getMaterial();
        this.scale = picture.getScale();
        this.description = picture.getDescription();
        this.height = picture.getHeight();
        this.width = picture.getWidth();
        this.location = picture.getLocation();
        this.hashtags = hashtags;
    }
}
