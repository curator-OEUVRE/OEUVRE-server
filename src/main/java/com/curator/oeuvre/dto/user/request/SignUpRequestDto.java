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
@ApiModel(value = "👤 회원가입 API Request")
public class SignUpRequestDto {

    @ApiModelProperty(notes = "이메일", example = "sobaek2000@naver.com")
    @Email
    @NotNull
    private String email;

    @ApiModelProperty(notes = "가입유형", example = "KAKAO")
    @NotNull
    @NotEmpty
    private String type;

    @ApiModelProperty(notes = "아이디", example = "one_zzini_")
    @Length(min = 4, max = 15)
    @NotNull
    @NotEmpty
    private String id;

    @ApiModelProperty(notes = "이름", example = "김원진")
    @Length(min = 2, max = 10)
    @NotNull
    @NotEmpty
    private String name;

    @ApiModelProperty(notes = "생일", example = "2000-06-21")
    @Length(max = 10)
    @Nullable
    private String birthday;

    @ApiModelProperty(notes = "프로필 이미지 url", example = "image_url")
    @Nullable
    private String profileImageUrl;

    @ApiModelProperty(notes = "배경 이미지 url", example = "image_url")
    @Nullable
    private String backgroundImageUrl;

    @ApiModelProperty(notes = "전시회 이름", example = "원진이의 전시회")
    @Length(min = 2, max = 15)
    @NotNull
    @NotEmpty
    private String exhibitionName;

    @ApiModelProperty(notes = "자기 소개", example = "사진찍기를 좋아하는 김원진입니덩")
    @Length(max = 20)
    @Nullable
    private String introduceMessage;

    @ApiModelProperty(notes = "알림 허용 여부", example = "true")
    @BooleanFlag
    private Boolean isAlarmOn;

}
