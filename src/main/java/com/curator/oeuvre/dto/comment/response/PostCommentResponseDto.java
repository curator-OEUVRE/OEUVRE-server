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
@ApiModel(value = "π λ°©λͺλ‘ λκΈ μμ± API Response")
public class PostCommentResponseDto {

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

    @ApiModelProperty(notes = "μμ± λ μ§", example = "2022-10-22 16:44:48")
    private final String createdAt;

    public PostCommentResponseDto(Comment comment) {
        this.commentNo = comment.getNo();
        this.userNo = comment.getUser().getNo();
        this.userId = comment.getUser().getId();
        this.profileImageUrl = comment.getUser().getProfileImageUrl();
        this.comment = comment.getComment();
        this.createdAt = comment.getCreatedAt().toString();
    }

}
