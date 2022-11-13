package com.curator.oeuvre.dto.notification.response;

import com.curator.oeuvre.domain.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@ApiModel(value = "🔔 알림 전체 조회 API Response")
public class GetNotificationResponseDto {

    @ApiModelProperty(notes = "알림 타입", example = "FOLLOWING / LIKES / COMMENT")
    private final String type;

    @ApiModelProperty(notes = "보낸 회원 no", example = "1")
    private final Long sendUserNo;

    @ApiModelProperty(notes = "보낸 회원 아이디", example = "one_zzini_")
    private final String id;

    @ApiModelProperty(notes = "보낸 회원 프로필 이미지 url", example = "image_url")
    private final String profileImageUrl;

    @ApiModelProperty(notes = "팔로잉 여부", example = "false")
    private final Boolean isFollowing;

    @ApiModelProperty(notes = "플로어 no", example = "1")
    private final Long floorNo;

    @ApiModelProperty(notes = "사진 no", example = "1")
    private final Long pictureNo;

    @ApiModelProperty(notes = "댓글 no", example = "1")
    private final Long commentNo;

    @ApiModelProperty(notes = "댓글 내용", example = "1")
    private final String comment;

    @ApiModelProperty(notes = "읽음 여부", example = "false")
    private final Boolean isRead;

    @ApiModelProperty(notes = "생성 일시", example = "2022-10-22 16:44:48")
    private final String createdAt;

    public GetNotificationResponseDto (
            Notification notification,
            Boolean isFollowing,
            Long floorNo,
            Long pictureNo,
            Long commentNo,
            String comment
    ) {
        this.type = notification.getType();
        this.sendUserNo = notification.getSendUser().getNo();
        this.id = notification.getSendUser().getId();
        this.profileImageUrl = notification.getSendUser().getProfileImageUrl();
        this.isFollowing = isFollowing;
        this.floorNo = floorNo;
        this.pictureNo = pictureNo;
        this.commentNo = commentNo;
        this.comment = comment;
        this.isRead = notification.getIsRead();
        this.createdAt = notification.getCreatedAt().toString();
    }
}


