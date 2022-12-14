package com.curator.oeuvre.dto.user.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel(value = "π€ λ΄ νλ‘ν νΈμ§ API Request")
public class PatchMyProfileRequestDto {

    @ApiModelProperty(notes = "μ΄λ¦", example = "κΉμμ§")
    @Length(min = 2, max = 10)
    @NotNull
    @NotEmpty
    private String name;

    @ApiModelProperty(notes = "μ μν μ΄λ¦", example = "μμ§μ΄μ μ μν")
    @Length(min = 2, max = 10)
    @NotNull
    @NotEmpty
    private String exhibitionName;

    @ApiModelProperty(notes = "μκΈ° μκ°", example = "μ¬μ§μ°κΈ°λ₯Ό μ’μνλ κΉμμ§μλλ©")
    @Length(max = 20)
    @Nullable
    private String introduceMessage;

    @ApiModelProperty(notes = "νλ‘ν μ΄λ―Έμ§ url", example = "image_url")
    @Nullable
    private String profileImageUrl;

    @ApiModelProperty(notes = "λ°°κ²½ μ΄λ―Έμ§ url", example = "image_url")
    @Nullable
    private String backgroundImageUrl;

}