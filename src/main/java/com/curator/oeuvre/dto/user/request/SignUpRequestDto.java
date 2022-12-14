package com.curator.oeuvre.dto.user.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jdk.jfr.BooleanFlag;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.lang.Nullable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel(value = "π€ νμκ°μ API Request")
public class SignUpRequestDto {

    @ApiModelProperty(notes = "μ΄λ©μΌ", example = "sobaek2000@naver.com")
    @Email
    @NotNull
    private String email;

    @ApiModelProperty(notes = "κ°μμ ν", example = "KAKAO")
    @NotNull
    @NotEmpty
    private String type;

    @ApiModelProperty(notes = "μμ΄λ", example = "one_zzini_")
    @Length(min = 4, max = 15)
    @NotNull
    @NotEmpty
    private String id;

    @ApiModelProperty(notes = "μ΄λ¦", example = "κΉμμ§")
    @Length(min = 2, max = 10)
    @NotNull
    @NotEmpty
    private String name;

    @ApiModelProperty(notes = "μμΌ", example = "2000-06-21")
    @Length(max = 10)
    @Nullable
    private String birthday;

    @ApiModelProperty(notes = "νλ‘ν μ΄λ―Έμ§ url", example = "image_url")
    @Nullable
    private String profileImageUrl;

    @ApiModelProperty(notes = "λ°°κ²½ μ΄λ―Έμ§ url", example = "image_url")
    @Nullable
    private String backgroundImageUrl;

    @ApiModelProperty(notes = "μ μν μ΄λ¦", example = "μμ§μ΄μ μ μν")
    @Length(min = 2, max = 10)
    @NotNull
    @NotEmpty
    private String exhibitionName;

    @ApiModelProperty(notes = "μκΈ° μκ°", example = "μ¬μ§μ°κΈ°λ₯Ό μ’μνλ κΉμμ§μλλ©")
    @Length(max = 20)
    @Nullable
    private String introduceMessage;

    @ApiModelProperty(notes = "μλ¦Ό νμ© μ¬λΆ", example = "true")
    @BooleanFlag
    private Boolean isAlarmOn;

}
