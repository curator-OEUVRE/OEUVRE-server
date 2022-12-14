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
@ApiModel(value = "π λ°©λͺλ‘ λκΈ μμ± API Request")
public class PostCommentRequestDto {

    @ApiModelProperty(notes = "λκΈ λ΄μ©", example = "μμ²­λ μ μλ€μ!!")
    @NotNull
    @NotEmpty
    private String comment;

}
