package com.curator.oeuvre.dto.floor.response;

import com.curator.oeuvre.domain.Floor;
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

    @ApiModelProperty(notes = "플로어 이름", example = "제주 여행 기록")
    private final String name;

    @ApiModelProperty(notes = "플로어 층", example = "5")
    private final Integer queue;

    @ApiModelProperty(notes = "전시회 이름", example = "원진이의 전시회")
    private final String exhibitionName;

    @ApiModelProperty(notes = "회원 no", example = "1")
    private final Long userNo;

    @ApiModelProperty(notes = "아이디", example = "one_zzini_")
    private final String userId;

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
            Floor floor,
            Boolean isMine,
            List<GetFloorPictureDto> pictures
    ) {
        this.floorNo = floor.getNo();
        this.name = floor.getName();
        this.queue = floor.getQueue();
        this.exhibitionName = floor.getUser().getExhibitionName();
        this.userNo = floor.getUser().getNo();
        this.userId = floor.getUser().getId();
        this.color = floor.getColor();
        this.texture = floor.getTexture();
        this.isPublic = floor.getIsPublic();
        this.isCommentAvailable = floor.getIsCommentAvailable();
        this.isMine = isMine;
        this.pictures = pictures;
    }
}