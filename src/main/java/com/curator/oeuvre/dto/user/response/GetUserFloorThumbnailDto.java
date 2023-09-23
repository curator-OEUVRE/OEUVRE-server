package com.curator.oeuvre.dto.user.response;

import com.curator.oeuvre.domain.Picture;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
public class GetUserFloorThumbnailDto {

    @ApiModelProperty(notes = "이미지 url", example = "image_url")
    private final String imageUrl;

    @ApiModelProperty(notes = "작은 이미지 url", example = "image_url")
    private final String smallImageUrl;

    @ApiModelProperty(notes = "세로 길이", example = "188")
    private final Float height;

    @ApiModelProperty(notes = "가로 길이", example = "70")
    private final Float width;

    public GetUserFloorThumbnailDto (Picture picture) {
        this.imageUrl = picture.getImageUrl();
        this.smallImageUrl = picture.getSmallImageUrl();
        this.height = picture.getHeight();
        this.width = picture.getWidth();
    }
}
