package com.curator.oeuvre.dto.comment.response;

import com.curator.oeuvre.domain.Comment;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@ApiModel(value = "📖 방명록 조회 API Response")
public class GetCommentResponseDto {

    @ApiModelProperty(notes = "댓글 no", example = "1")
    private final Long commentNo;

    @ApiModelProperty(notes = "회원 no", example = "1")
    private final Long userNo;

    @ApiModelProperty(notes = "회원 id", example = "one_zzini")
    private final String userId;

    @ApiModelProperty(notes = "회원 프로필 사진", example = "image-url")
    private final String profileImageUrl;

    @ApiModelProperty(notes = "댓글 내용", example = "엄청난 전시네요!")
    private final String comment;

    @ApiModelProperty(notes = "내 댓글 여부", example = "false")
    private final Boolean isMine;

    @ApiModelProperty(notes = "생성 일시", example = "2022-10-22 16:44:48")
    private final String createdAt;

    public GetCommentResponseDto(Comment comment, Boolean isMine) {
        this.commentNo = comment.getNo();
        this.userNo = comment.getUser().getNo();
        this.userId = comment.getUser().getId();
        this.profileImageUrl = comment.getUser().getProfileImageUrl();
        this.comment = comment.getComment();
        this.isMine = isMine;
        this.createdAt = comment.getCreatedAt().toString();
    }

}
