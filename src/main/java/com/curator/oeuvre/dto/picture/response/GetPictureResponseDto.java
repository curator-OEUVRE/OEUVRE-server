package com.curator.oeuvre.dto.picture.response;

import com.curator.oeuvre.domain.Picture;
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

    @ApiModelProperty(notes = "회원 no", example = "1")
    private final Long userNo;

    @ApiModelProperty(notes = "회원 아이디", example = "one_zzini_")
    private final String userId;

    @ApiModelProperty(notes = "플로어 no", example = "1")
    private final Long floorNo;

    @ApiModelProperty(notes = "이미지 url", example = "image_url")
    private final String imageUrl;

    @ApiModelProperty(notes = "사진 설명", example = "노을을 보면서 한컷")
    private final String description;

    @ApiModelProperty(notes = "세로 길이", example = "188")
    private final Float height;

    @ApiModelProperty(notes = "세로 길이", example = "70")
    private final Float width;

    @ApiModelProperty(notes = "본인 사진 여부", example = "true")
    private final Boolean isMine;

    @ApiModelProperty(notes = "좋아요 여부", example = "true")
    private final Boolean isLiked;

    @ApiModelProperty(notes = "스크랩 여부", example = "true")
    private final Boolean isScraped;

    public GetPictureResponseDto (
            Picture picture,
            Boolean isMine,
            Boolean isLiked,
            Boolean isScraped
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
    }
}
