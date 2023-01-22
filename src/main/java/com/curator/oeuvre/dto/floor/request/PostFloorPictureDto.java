package com.curator.oeuvre.dto.floor.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostFloorPictureDto {

    @ApiModelProperty(notes = "사진 순서", example = "1")
    @NotNull(message = "사진 순서를 입력해주세요")
    private Integer queue;

    @ApiModelProperty(notes = "이미지 url", example = "image_url")
    @NotNull(message = "이미지 url을 입력해주세요")
    private String imageUrl;

    @ApiModelProperty(notes = "작품 제목", example = "노을")
    private String title;

    @ApiModelProperty(notes = "작품 설명", example = "노을을 보면서 한컷")
    private String description;

    @ApiModelProperty(notes = "제작년도", example = "2023")
    private String manufactureYear;

    @ApiModelProperty(notes = "작품 재료", example = "캔버스에 유채")
    private String material;

    @ApiModelProperty(notes = "작품 크기", example = "22*88(cm)")
    private String scale;

    @ApiModelProperty(notes = "세로 길이", example = "188")
    @NotNull
    private Float height;

    @ApiModelProperty(notes = "가로 길이", example = "70")
    @NotNull
    private Float width;

    @ApiModelProperty(notes = "y축 위치", example = "93")
    private Float location;

    @ApiModelProperty(notes = "해시태그 목록", example = "[\"노을\", \"바다\", \"제주\"]")
    private List<String> hashtags;
}
