package com.curator.oeuvre.dto.comment.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
@EqualsAndHashCode
@ApiModel(value = "플로어조회 API Response")
public class GetFloorCommentsResponseDto {

    @ApiModelProperty(notes = "플로어 이름", example = "제주여행")
    private final String floorName;

    @ApiModelProperty(notes = "마지막 페이지 여부", example = "false")
    private final Boolean isLastPage;

    @ApiModelProperty(notes = "내용", example = "[]")
    private final List<GetCommentResponseDto> contents;

    public GetFloorCommentsResponseDto(String floorName, Boolean isLastPage, List<GetCommentResponseDto> contents) {
        this.floorName = floorName;
        this.isLastPage = isLastPage;
        this.contents = contents;
    }

}