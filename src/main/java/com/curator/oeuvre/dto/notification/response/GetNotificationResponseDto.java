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
@ApiModel(value = "π μλ¦Ό μ μ²΄ μ‘°ν API Response")
public class GetNotificationResponseDto {

    @ApiModelProperty(notes = "μλ¦Ό νμ", example = "FOLLOWING / LIKES / COMMENT")
    private final String type;

    @ApiModelProperty(notes = "λ³΄λΈ νμ no", example = "1")
    private final Long sendUserNo;

    @ApiModelProperty(notes = "λ³΄λΈ νμ μμ΄λ", example = "one_zzini_")
    private final String id;

    @ApiModelProperty(notes = "λ³΄λΈ νμ νλ‘ν μ΄λ―Έμ§ url", example = "image_url")
    private final String profileImageUrl;

    @ApiModelProperty(notes = "νλ‘μ μ¬λΆ", example = "false")
    private final Boolean isFollowing;

    @ApiModelProperty(notes = "νλ‘μ΄ no", example = "1")
    private final Long floorNo;

    @ApiModelProperty(notes = "μ¬μ§ no", example = "1")
    private final Long pictureNo;

    @ApiModelProperty(notes = "λκΈ no", example = "1")
    private final Long commentNo;

    @ApiModelProperty(notes = "λκΈ λ΄μ©", example = "1")
    private final String comment;

    @ApiModelProperty(notes = "μ½μ μ¬λΆ", example = "false")
    private final Boolean isRead;

    @ApiModelProperty(notes = "μμ± μΌμ", example = "2022-10-22 16:44:48")
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


