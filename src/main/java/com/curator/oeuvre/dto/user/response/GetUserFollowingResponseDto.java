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
@ApiModel(value = "π€ μ μ  νλ‘μ μ μ²΄ μ‘°ν API Response")
public class GetUserFollowingResponseDto {

    @ApiModelProperty(notes = "νμ no", example = "1")
    private final Long userNo;

    @ApiModelProperty(notes = "νλ‘ν μ΄λ―Έμ§ url", example = "image_url")
    private final String profileImageUrl;

    @ApiModelProperty(notes = "μμ΄λ", example = "one_zzini_")
    private final String id;

    @ApiModelProperty(notes = "μ΄λ¦", example = "κΉμμ§")
    private final String name;

    @ApiModelProperty(notes = "νλ‘μ μ¬λΆ", example = "false")
    private final Boolean isFollowing;

    @ApiModelProperty(notes = "νλ‘μ μ¬λΆ", example = "false")
    private final Boolean isFollower;

    @ApiModelProperty(notes = "λ μ¬λΆ", example = "false")
    private final Boolean isMe;

    public GetUserFollowingResponseDto (
            User user,
            Boolean isFollowing,
            Boolean isFollower,
            Boolean isMe
    ) {
        this.userNo = user.getNo();
        this.profileImageUrl = user.getProfileImageUrl();
        this.id = user.getId();
        this.name = user.getName();
        this.isFollowing = isFollowing;
        this.isFollower = isFollower;
        this.isMe = isMe;
    }
}