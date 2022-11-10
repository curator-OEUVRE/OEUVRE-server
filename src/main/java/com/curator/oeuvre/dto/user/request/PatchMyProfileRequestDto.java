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
@ApiModel(value = "👤 내 프로필 편집 API Request")
public class PatchMyProfileRequestDto {

    @ApiModelProperty(notes = "이름", example = "김원진")
    @Length(min = 2, max = 10)
    @NotNull
    @NotEmpty
    private String name;

    @ApiModelProperty(notes = "전시회 이름", example = "원진이의 전시회")
    @Length(min = 2, max = 10)
    @NotNull
    @NotEmpty
    private String exhibitionName;

    @ApiModelProperty(notes = "자기 소개", example = "사진찍기를 좋아하는 김원진입니덩")
    @Length(max = 20)
    @Nullable
    private String introduceMessage;

    @ApiModelProperty(notes = "프로필 이미지 url", example = "image_url")
    @Nullable
    private String profileImageUrl;

    @ApiModelProperty(notes = "배경 이미지 url", example = "image_url")
    @Nullable
    private String backgroundImageUrl;

}