package com.curator.oeuvre.dto.picture.response;

import com.curator.oeuvre.domain.Picture;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
@EqualsAndHashCode
@ApiModel(value = "๐ ์ฌ์ง ์กฐํ API Response")
public class GetPictureResponseDto {

    @ApiModelProperty(notes = "์ฌ์ง no", example = "1")
    private final Long pictureNo;

    @ApiModelProperty(notes = "ํ์ no", example = "1")
    private final Long userNo;

    @ApiModelProperty(notes = "ํ์ ์์ด๋", example = "one_zzini_")
    private final String userId;

    @ApiModelProperty(notes = "ํ๋ก์ด no", example = "1")
    private final Long floorNo;

    @ApiModelProperty(notes = "์ด๋ฏธ์ง url", example = "image_url")
    private final String imageUrl;

    @ApiModelProperty(notes = "์ฌ์ง ์ค๋ช", example = "๋ธ์์ ๋ณด๋ฉด์ ํ์ปท")
    private final String description;

    @ApiModelProperty(notes = "์ธ๋ก ๊ธธ์ด", example = "188")
    private final Float height;

    @ApiModelProperty(notes = "์ธ๋ก ๊ธธ์ด", example = "70")
    private final Float width;

    @ApiModelProperty(notes = "๋ณธ์ธ ์ฌ์ง ์ฌ๋ถ", example = "true")
    private final Boolean isMine;

    @ApiModelProperty(notes = "์ข์์ ์ฌ๋ถ", example = "true")
    private final Boolean isLiked;

    @ApiModelProperty(notes = "์คํฌ๋ฉ ์ฌ๋ถ", example = "true")
    private final Boolean isScraped;

    @ApiModelProperty(notes = "ํด์ํ๊ทธ ๋ชฉ๋ก", example = "[\"#๋ธ์\", \"#๋ฐ๋ค\", \"#์ ์ฃผ\"]")
    private final List<String> hashtags;

    public GetPictureResponseDto (
            Picture picture,
            Boolean isMine,
            Boolean isLiked,
            Boolean isScraped,
            List<String> hashtags
    ) {
        this.pictureNo = picture.getNo();
        this.userNo = picture.getFloor().getUser().getNo();
        this.userId = picture.getFloor().getUser().getId();
        this.floorNo = picture.getFloor().getNo();
        this.imageUrl = picture.getImageUrl();
        this.description = picture.getDescription();
        this.height = picture.getHeight();
        this.width = picture.getWidth();
        this.isMine = isMine;
        this.isLiked = isLiked;
        this.isScraped = isScraped;
        this.hashtags = hashtags;
    }
}
