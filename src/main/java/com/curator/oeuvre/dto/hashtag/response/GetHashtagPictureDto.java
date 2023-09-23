package com.curator.oeuvre.dto.hashtag.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
@Setter
@EqualsAndHashCode
public class GetHashtagPictureDto {

    @ApiModelProperty(notes = "사진 no", example = "1")
    private final Long pictureNo;

    @ApiModelProperty(notes = "이미지 url", example = "image_url")
    private final String imageUrl;

    @ApiModelProperty(notes = "작은 이미지 url", example = "image_url")
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

    @ApiModelProperty(notes = "세로 길이", example = "188")
    private final Float height;

    @ApiModelProperty(notes = "가로 길이", example = "70")
    private final Float width;

    @ApiModelProperty(notes = "내 사진 여부", example = "false")
    private final Boolean isMine;

    @ApiModelProperty(notes = "좋아요 여부", example = "true")
    private final Boolean isLiked;

    @ApiModelProperty(notes = "스크랩 여부", example = "true")
    private final Boolean isScraped;

    @ApiModelProperty(notes = "플로어 no", example = "1")
    private final Long floorNo;

    @ApiModelProperty(notes = "회원 no", example = "1")
    private final Long userNo;

    @ApiModelProperty(notes = "회원 아이디", example = "one_zzini_")
    private final String userId;

    @ApiModelProperty(notes = "프로필 이미지 url", example = "image_url")
    private final String profileImageUrl;

    public GetHashtagPictureDto (
            Long pictureNo,
            String imageUrl,
            String smallImageUrl,
            String title,
            String manufactureYear,
            String material,
            String scale,
            String description,
            Float height,
            Float width,
            Boolean isMine,
            Boolean isLiked,
            Boolean isScraped,
            Long floorNo,
            Long userNo,
            String userId,
            String profileImageUrl
    ) {
        this.pictureNo = pictureNo;
        this.imageUrl = imageUrl;
        this.smallImageUrl = smallImageUrl;
        this.title = title;
        this.manufactureYear = manufactureYear;
        this.material = material;
        this.scale = scale;
        this.description = description;
        this.height = height;
        this.width = width;
        this.isMine = isMine;
        this.isLiked = isLiked;
        this.isScraped = isScraped;
        this.floorNo = floorNo;
        this.userNo = userNo;
        this.userId = userId;
        this.profileImageUrl = profileImageUrl;
    }
}
