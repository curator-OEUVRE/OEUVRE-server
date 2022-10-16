package com.curator.oeuvre.dto.user.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jdk.jfr.BooleanFlag;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.lang.Nullable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

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
    private String type;

    @ApiModelProperty(notes = "아이디", example = "one_zzini_")
    @Length(min = 4, max = 15)
    @NotNull
    private String id;

    @ApiModelProperty(notes = "이름", example = "김원진")
    @Length(min = 2, max = 10)
    @NotNull
    private String name;

    @ApiModelProperty(notes = "생일", example = "2000-06-21")
    @Length(min = 10, max = 10)
    @NotNull
    private String birthday;

    @ApiModelProperty(notes = "프로필 이미지 url", example = "image_url")
    @Nullable
    private String profileImageUrl;

    @ApiModelProperty(notes = "전시회 이름", example = "원진이의 전시회")
    @Length(min = 2, max = 10)
    @NotNull
    private String exhibitionName;

    @ApiModelProperty(notes = "자기 소개", example = "안녕하세요 사진찍기를 좋아하는 김원진입니덩")
    @Length(max = 50)
    @Nullable
    private String introduceMessage;

    @Schema
    @ApiModelProperty(notes = "알림 허용 여부", example = "true")
    @BooleanFlag
    private Boolean isAlarmOn;

}
