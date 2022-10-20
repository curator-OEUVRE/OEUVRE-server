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

    @Length(max = 50)
    @ApiModelProperty(notes = "사진 설명", example = "노을을 보면서 한컷")
    private String description;

    @ApiModelProperty(notes = "해시태그 목록", example = "[\"#노을\", \"#바다\", \"#제주\"]")
    private List<String> hashtags;
}
