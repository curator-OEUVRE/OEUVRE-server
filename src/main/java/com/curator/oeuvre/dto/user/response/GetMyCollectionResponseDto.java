package com.curator.oeuvre.dto.user.response;

import com.curator.oeuvre.domain.Picture;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@ApiModel(value = "👤 내 컬렉션 전체 조회 API Response")

public class GetMyCollectionResponseDto {

    @ApiModelProperty(notes = "사진 no", example = "1")
    private final Long pictureNo;

    @ApiModelProperty(notes = "이미지 url", example = "image_url")
    private final String imageUrl;

    @ApiModelProperty(notes = "세로 길이", example = "188")
    private final Float height;

    @ApiModelProperty(notes = "세로 길이", example = "70")
    private final Float width;

    public GetMyCollectionResponseDto (Picture picture) {
        this.pictureNo = picture.getNo();
        this.imageUrl = picture.getImageUrl();
        this.height = picture.getHeight();
        this.width = picture.getWidth();
    }
}