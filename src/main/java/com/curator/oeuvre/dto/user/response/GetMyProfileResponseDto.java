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
@ApiModel(value = "π€ λ΄ νλ‘ν μ λ³΄ μ‘°ν API Response")

public class GetMyProfileResponseDto {

    @ApiModelProperty(notes = "νμ no", example = "1")
    private final Long userNo;

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

    @ApiModelProperty(notes = "λ°©λͺλ‘ μλ¦Ό νμ© μ¬λΆ", example = "true")
    private final Boolean isCommentAlarmOn;

    @ApiModelProperty(notes = "λ©μΈμ§ μλ¦Ό νμ© μ¬λΆ", example = "true")
    private final Boolean isMessageAlarmOn;

    @ApiModelProperty(notes = "μ’μμ μλ¦Ό νμ© μ¬λΆ", example = "true")
    private final Boolean isLikeAlarmOn;

    @ApiModelProperty(notes = "νλ‘μ° μλ¦Ό νμ© μ¬λΆ", example = "true")
    private final Boolean isFollowAlarmOn;

    @ApiModelProperty(notes = "κ·Έλ£Ήμ μ μλ¦Ό νμ© μ¬λΆ", example = "true")
    private final Boolean isGroupExhibitionAlarmOn;

    public GetMyProfileResponseDto(User user, Long followingCount, Long followerCount) {
        this.userNo = user.getNo();
        this.id = user.getId();
        this.name = user.getName();
        this.profileImageUrl = user.getProfileImageUrl();
        this.backgroundImageUrl = user.getBackgroundImageUrl();
        this.exhibitionName = user.getExhibitionName();
        this.introduceMessage = user.getIntroduceMessage();
        this.followingCount = followingCount;
        this.followerCount = followerCount;
        this.isCommentAlarmOn = user.getIsCommentAlarmOn();
        this.isMessageAlarmOn = user.getIsMessageAlarmOn();
        this.isLikeAlarmOn = user.getIsLikeAlarmOn();
        this.isFollowAlarmOn = user.getIsFollowAlarmOn();
        this.isGroupExhibitionAlarmOn = user.getIsGroupRequestAlarmOn();
    }
}