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
@ApiModel(value = "📖 방명록 댓글 생성 API Response")
public class PostCommentResponseDto {

    @ApiModelProperty(notes = "댓글 no", example = "1")
    private final Long commentNo;

    public PostCommentResponseDto(Comment comment) {
        this.commentNo = comment.getNo();
    }

}
