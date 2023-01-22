package com.curator.oeuvre.dto.picture.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Validated
@ApiModel(value = "🌃 사진 설명 수정 API Request")

public class PatchPictureRequestDto {

    @Length(max = 20)
    @ApiModelProperty(notes = "작품 제목", example = "노을")
    private String title;

    @Length(max = 50)
    @ApiModelProperty(notes = "작품 설명", example = "노을을 보면서 한컷")
    private String description;

    @ApiModelProperty(notes = "제작년도", example = "2023")
    private String manufactureYear;

    @ApiModelProperty(notes = "작품 재료", example = "캔버스에 유채")
    private String material;

    @ApiModelProperty(notes = "작품 크기", example = "22*88(cm)")
    private String scale;

    @ApiModelProperty(notes = "해시태그 목록", example = "[\"노을\", \"바다\", \"제주\"]")
    private List<String> hashtags;
}
