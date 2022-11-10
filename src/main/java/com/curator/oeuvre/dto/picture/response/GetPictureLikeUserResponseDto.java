package com.curator.oeuvre.dto.picture.response;

import com.curator.oeuvre.domain.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@ApiModel(value = "🌃 사진 좋아요 한 유저 전체 조회 API Response")
public class GetPictureLikeUserResponseDto {

    @ApiModelProperty(notes = "회원 no", example = "1")
    private final Long userNo;

    @ApiModelProperty(notes = "프로필 이미지 url", example = "image_url")
    private final String profileImageUrl;

    @ApiModelProperty(notes = "아이디", example = "one_zzini_")
    private final String id;

    @ApiModelProperty(notes = "이름", example = "김원진")
    private final String name;


    public GetPictureLikeUserResponseDto (
            User user
    ) {
        this.userNo = user.getNo();
        this.profileImageUrl = user.getProfileImageUrl();
        this.id = user.getId();
        this.name = user.getName();
    }
}

