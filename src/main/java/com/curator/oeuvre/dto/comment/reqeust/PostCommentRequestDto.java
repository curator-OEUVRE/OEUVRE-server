package com.curator.oeuvre.dto.comment.reqeust;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel(value = "📖 방명록 댓글 생성 API Request")
public class PostCommentRequestDto {

    @ApiModelProperty(notes = "댓글 내용", example = "엄청난 전시네요!!")
    @NotNull
    @NotEmpty
    private String comment;

}
