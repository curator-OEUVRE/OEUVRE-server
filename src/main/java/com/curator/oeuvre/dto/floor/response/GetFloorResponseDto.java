package com.curator.oeuvre.dto.floor.response;

import com.curator.oeuvre.dto.floor.request.PostFloorPictureDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
@EqualsAndHashCode
@ApiModel(value = "🎞 플로어 조회 API Response")
public class GetFloorResponseDto {

    @ApiModelProperty(notes = "플로어 no", example = "1")
    private final Long floorNo;

    @ApiModelProperty(notes = "회원 no", example = "1")
    private final Long userNo;

    @ApiModelProperty(notes = "플로어 이름", example = "제주 여행 기록")
    private final String name;

    @ApiModelProperty(notes = "배경 색상코드", example = "#FFFFFF")
    private final String color;

    @ApiModelProperty(notes = "배경 질감", example = "0")
    private final Integer texture;

    @ApiModelProperty(notes = "공개 여부", example = "true")
    private final Boolean isPublic;

    @ApiModelProperty(notes = "방명록 허용 여부", example = "true")
    private final Boolean isCommentAvailable;

    @ApiModelProperty(notes = "본인 플로어 여부", example = "true")
    private final Boolean isMine;

    @ApiModelProperty(notes = "사진 목록")
    private final List<GetFloorPictureDto> pictures;

    public GetFloorResponseDto(
            Long floorNo,
            Long userNo,
            String name,
            String color,
            Integer texture,
            Boolean isPublic,
            Boolean isCommentAvailable,
            Boolean isMine,
            List<GetFloorPictureDto> pictures
    ) {
        this.floorNo = floorNo;
        this.userNo = userNo;
        this.name = name;
        this.color = color;
        this.texture = texture;
        this.isPublic = isPublic;
        this.isCommentAvailable = isCommentAvailable;
        this.isMine = isMine;
        this.pictures = pictures;
    }

}