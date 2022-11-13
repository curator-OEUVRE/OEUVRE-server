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

    @ApiModelProperty(notes = "세로 길이", example = "188")
    private final Float height;

    @ApiModelProperty(notes = "가로 길이", example = "70")
    private final Float width;

    @ApiModelProperty(notes = "회원 no", example = "1")
    private final Long userNo;

    @ApiModelProperty(notes = "아이디", example = "one_zzini_")
    private final String id;

    @ApiModelProperty(notes = "프로필 이미지 url", example = "image_url")
    private final String profileImageUrl;

    @ApiModelProperty(notes = "내 사진 여부", example = "false")
    private final Boolean isMine;

    public GetHashtagPictureDto (
            Long pictureNo,
            String imageUrl,
            Float height,
            Float width,
            Long userNo,
            String id,
            String profileImageUrl,
            Boolean isMine
    ) {
        this.pictureNo = pictureNo;
        this.imageUrl = imageUrl;
        this.height = height;
        this.width = width;
        this.userNo = userNo;
        this.id = id;
        this.profileImageUrl = profileImageUrl;
        this.isMine = isMine;
    }
}
