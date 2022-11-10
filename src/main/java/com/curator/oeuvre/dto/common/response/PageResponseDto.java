package com.curator.oeuvre.dto.common.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@ApiModel(value = "페이지네이션 API Response")
public class PageResponseDto<T> {

    @ApiModelProperty(notes = "마지막 페이지 여부", example = "false")
    private final Boolean isLastPage;

    @ApiModelProperty(notes = "내용", example = "[]")
    private final T contents;

    public PageResponseDto(Boolean isLastPage, T contents) {
        this.isLastPage = isLastPage;
        this.contents = contents;
    }

}