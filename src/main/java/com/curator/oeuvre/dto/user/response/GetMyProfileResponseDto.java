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
@ApiModel(value = "👤 내 프로필 정보 조회 API Response")

public class GetMyProfileResponseDto {

    @ApiModelProperty(notes = "아이디", example = "one_zzini_")
    private final String id;

    @ApiModelProperty(notes = "이름", example = "김원진")
    private final String name;

    @ApiModelProperty(notes = "프로필 이미지 url", example = "image_url")
    private final String profileImageUrl;

    @ApiModelProperty(notes = "배경 이미지 url", example = "image_url")
    private final String backgroundImageUrl;

    @ApiModelProperty(notes = "전시회 이름", example = "원진이의 전시회")
    private final String exhibitionName;

    @ApiModelProperty(notes = "자기 소개", example = "안녕하세요 사진찍기를 좋아하는 김원진입니덩")
    private final String introduceMessage;

    @ApiModelProperty(notes = "팔로잉 수", example = "999")
    private final Long followingCount;

    @ApiModelProperty(notes = "팔로워 수", example = "980")
    private final Long followerCount;

    @ApiModelProperty(notes = "방명록 알림 허용 여부", example = "true")
    private final Boolean isCommentAlarmOn;

    @ApiModelProperty(notes = "메세지 알림 허용 여부", example = "true")
    private final Boolean isMessageAlarmOn;

    @ApiModelProperty(notes = "좋아요 알림 허용 여부", example = "true")
    private final Boolean isLikeAlarmOn;

    @ApiModelProperty(notes = "팔로우 알림 허용 여부", example = "true")
    private final Boolean isFollowAlarmOn;

    @ApiModelProperty(notes = "그룹전시 알림 허용 여부", example = "true")
    private final Boolean isGroupExhibitionAlarmOn;

    public GetMyProfileResponseDto(User user, Long followingCount, Long followerCount) {
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