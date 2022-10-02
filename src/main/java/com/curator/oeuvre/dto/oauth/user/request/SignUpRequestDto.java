package com.curator.oeuvre.dto.oauth.user.request;

import io.swagger.annotations.ApiModelProperty;
import jdk.jfr.BooleanFlag;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.lang.Nullable;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignUpRequestDto {

    @ApiModelProperty(example = "sobaek2000@naver.com")
    @Email
    @NotNull
    private String email;

    @ApiModelProperty(example = "KAKAO")
    @NotNull
    private String type;

    @ApiModelProperty(example = "one_zzini_")
    @Length(min = 4, max = 15)
    @NotNull
    private String id;

    @ApiModelProperty(example = "김원진")
    @Length(min = 2, max = 10)
    @NotNull
    private String name;

    @ApiModelProperty(example = "2000-06-21")
    @Length(min = 10, max = 10)
    @NotNull
    private String birthday;

    @ApiModelProperty(example = "image_url")
    @Nullable
    private String profileImageUrl;

    @ApiModelProperty(example = "원진이의 전시회")
    @Length(min = 2, max = 10)
    @NotNull
    private String exhibitionName;

    @ApiModelProperty(example = "안녕하세요 사진찍기를 좋아하는 김원진입니덩")
    @Length(max = 50)
    @Nullable
    private String introduceMessage;

    @ApiModelProperty(example = "true")
    @BooleanFlag
    private Boolean isAlarmOn;

}
