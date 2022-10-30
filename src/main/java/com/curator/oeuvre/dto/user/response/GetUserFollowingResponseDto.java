package com.curator.oeuvre.dto.user.response;

import com.curator.oeuvre.domain.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@ApiModel(value = "👤 유저 팔로잉 전체 조회 API Response")
public class GetUserFollowingResponseDto {

    @ApiModelProperty(notes = "회원 no", example = "1")
    private final Long userNo;

    @ApiModelProperty(notes = "프로필 이미지 url", example = "image_url")
    private final String profileImageUrl;

    @ApiModelProperty(notes = "아이디", example = "one_zzini_")
    private final String id;

    @ApiModelProperty(notes = "이름", example = "김원진")
    private final String name;

    private final Boolean isFollowing;

    public GetUserFollowingResponseDto (
            User user,
            Boolean isFollowing
    ) {
        this.userNo = user.getNo();
        this.profileImageUrl = user.getProfileImageUrl();
        this.id = user.getId();
        this.name = user.getName();
        this.isFollowing = isFollowing;
    }
}