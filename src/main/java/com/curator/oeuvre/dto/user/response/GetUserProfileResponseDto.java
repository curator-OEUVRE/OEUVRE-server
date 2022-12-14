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
@ApiModel(value = "π€ ν μ μ  νλ‘ν μ λ³΄ μ‘°ν API Response")

public class GetUserProfileResponseDto {

    @ApiModelProperty(notes = "μμ΄λ", example = "one_zzini_")
    private final String id;

    @ApiModelProperty(notes = "μ΄λ¦", example = "κΉμμ§")
    private final String name;

    @ApiModelProperty(notes = "νλ‘ν μ΄λ―Έμ§ url", example = "image_url")
    private final String profileImageUrl;

    @ApiModelProperty(notes = "λ°°κ²½ μ΄λ―Έμ§ url", example = "image_url")
    private final String backgroundImageUrl;

    @ApiModelProperty(notes = "μ μν μ΄λ¦", example = "μμ§μ΄μ μ μν")
    private final String exhibitionName;

    @ApiModelProperty(notes = "μκΈ° μκ°", example = "μλνμΈμ μ¬μ§μ°κΈ°λ₯Ό μ’μνλ κΉμμ§μλλ©")
    private final String introduceMessage;

    @ApiModelProperty(notes = "νλ‘μ μ", example = "999")
    private final Long followingCount;

    @ApiModelProperty(notes = "νλ‘μ μ", example = "980")
    private final Long followerCount;

    @ApiModelProperty(notes = "νλ‘μ μ¬λΆ", example = "true")
    private final Boolean isFollower;

    @ApiModelProperty(notes = "νλ‘μ μ¬λΆ", example = "false")
    private final Boolean isFollowing;


    public GetUserProfileResponseDto(User user, Long followingCount, Long followerCount, Boolean isFollower, Boolean isFollowing) {
        this.id = user.getId();
        this.name = user.getName();
        this.profileImageUrl = user.getProfileImageUrl();
        this.backgroundImageUrl = user.getBackgroundImageUrl();
        this.exhibitionName = user.getExhibitionName();
        this.introduceMessage = user.getIntroduceMessage();
        this.followingCount = followingCount;
        this.followerCount = followerCount;
        this.isFollower = isFollower;
        this.isFollowing = isFollowing;
    }
}