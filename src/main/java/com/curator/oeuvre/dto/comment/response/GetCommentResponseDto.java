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
@ApiModel(value = "π λ°©λͺλ‘ μ‘°ν API Response")
public class GetCommentResponseDto {

    @ApiModelProperty(notes = "λκΈ no", example = "1")
    private final Long commentNo;

    @ApiModelProperty(notes = "νμ no", example = "1")
    private final Long userNo;

    @ApiModelProperty(notes = "νμ id", example = "one_zzini")
    private final String userId;

    @ApiModelProperty(notes = "νμ νλ‘ν μ¬μ§", example = "image-url")
    private final String profileImageUrl;

    @ApiModelProperty(notes = "λκΈ λ΄μ©", example = "μμ²­λ μ μλ€μ!")
    private final String comment;

    @ApiModelProperty(notes = "λ΄ λκΈ μ¬λΆ", example = "false")
    private final Boolean isMine;

    @ApiModelProperty(notes = "μμ± μΌμ", example = "2022-10-22 16:44:48")
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
