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
@ApiModel(value = "🌃 사진 조회 API Response")
public class GetPictureResponseDto {

    @ApiModelProperty(notes = "사진 순서", example = "1")
    private final Integer queue;

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

    @ApiModelProperty(notes = "작은 이미지 url", example = "small_image_url")
    private final String smallImageUrl;

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

    @ApiModelProperty(notes = "위치", example = "10")
    private final Float location;

    @ApiModelProperty(notes = "세로 길이", example = "188")
    private final Float height;

    @ApiModelProperty(notes = "가로 길이", example = "70")
    private final Float width;

    @ApiModelProperty(notes = "본인 사진 여부", example = "true")
    private final Boolean isMine;

    @ApiModelProperty(notes = "좋아요 여부", example = "true")
    private final Boolean isLiked;

    @ApiModelProperty(notes = "스크랩 여부", example = "true")
    private final Boolean isScraped;

    @ApiModelProperty(notes = "해시태그 목록", example = "[\"노을\", \"바다\", \"제주\"]")
    private final List<String> hashtags;

    public GetPictureResponseDto (
            Picture picture,
            Boolean isMine,
            Boolean isLiked,
            Boolean isScraped,
            List<String> hashtags
    ) {
        this.queue = picture.getQueue();
        this.pictureNo = picture.getNo();
        this.userNo = picture.getFloor().getUser().getNo();
        this.userId = picture.getFloor().getUser().getId();
        this.floorNo = picture.getFloor().getNo();
        this.imageUrl = picture.getImageUrl();
        this.smallImageUrl = picture.getSmallImageUrl();
        this.title = picture.getTitle();
        this.manufactureYear = picture.getManufactureYear();
        this.material = picture.getMaterial();
        this.scale = picture.getScale();
        this.description = picture.getDescription();
        this.location = picture.getLocation();
        this.height = picture.getHeight();
        this.width = picture.getWidth();
        this.isMine = isMine;
        this.isLiked = isLiked;
        this.isScraped = isScraped;
        this.hashtags = hashtags;
    }
}
