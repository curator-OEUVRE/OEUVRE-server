package com.curator.oeuvre.dto.user.response;

import com.curator.oeuvre.domain.Floor;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import java.util.List;

@Getter
@ToString
@EqualsAndHashCode
@ApiModel(value = "๐ค ์ ์  ํ๋ก์ด ์ ์ฒด ์กฐํ API Response")
public class GetUserFloorResponseDto {

    @ApiModelProperty(notes = "ํ๋ก์ด ์์", example = "1")
    private final Integer queue;

    @ApiModelProperty(notes = "ํ๋ก์ด no", example = "1")
    private final Long floorNo;

    @ApiModelProperty(notes = "ํ๋ก์ด ์ด๋ฆ", example = "์ ์ฃผ ์ฌํ ๊ธฐ๋ก")
    private final String name;

    @ApiModelProperty(notes = "๋ฐฐ๊ฒฝ ์์์ฝ๋", example = "#FFFFFF")
    private final String color;

    @ApiModelProperty(notes = "๋ฐฐ๊ฒฝ ์ง๊ฐ", example = "0")
    private final Integer texture;

    @ApiModelProperty(notes = "์ธ๋ค์ผ ๋ชฉ๋ก")
    private final List<GetUserFloorThumbnailDto> thumbnails;


    public GetUserFloorResponseDto(Floor floor, List<GetUserFloorThumbnailDto> thumbnails) {
        this.queue = floor.getQueue();
        this.floorNo = floor.getNo();
        this.name = floor.getName();
        this.color = floor.getColor();
        this.texture = floor.getTexture();
        this.thumbnails = thumbnails;
    }
}

