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
@ApiModel(value = "π νλ‘μ΄ μ‘°ν API Response")
public class GetFloorResponseDto {

    @ApiModelProperty(notes = "νλ‘μ΄ no", example = "1")
    private final Long floorNo;

    @ApiModelProperty(notes = "νλ‘μ΄ μ΄λ¦", example = "μ μ£Ό μ¬ν κΈ°λ‘")
    private final String name;

    @ApiModelProperty(notes = "νλ‘μ΄ μΈ΅", example = "5")
    private final Integer queue;

    @ApiModelProperty(notes = "μ μν μ΄λ¦", example = "μμ§μ΄μ μ μν")
    private final String exhibitionName;

    @ApiModelProperty(notes = "νμ no", example = "1")
    private final Long userNo;

    @ApiModelProperty(notes = "μμ΄λ", example = "one_zzini_")
    private final String userId;

    @ApiModelProperty(notes = "λ°°κ²½ μμμ½λ", example = "#FFFFFF")
    private final String color;

    @ApiModelProperty(notes = "λ°°κ²½ μ§κ°", example = "0")
    private final Integer texture;

    @ApiModelProperty(notes = "κ³΅κ° μ¬λΆ", example = "true")
    private final Boolean isPublic;

    @ApiModelProperty(notes = "λ°©λͺλ‘ νμ© μ¬λΆ", example = "true")
    private final Boolean isCommentAvailable;

    @ApiModelProperty(notes = "λ³ΈμΈ νλ‘μ΄ μ¬λΆ", example = "true")
    private final Boolean isMine;

    @ApiModelProperty(notes = "λκΈ μλ°μ΄νΈ μ¬λΆ", example = "true")
    private final Boolean hasNewComment;

    @ApiModelProperty(notes = "μ¬μ§ λͺ©λ‘")
    private final List<GetFloorPictureDto> pictures;

    public GetFloorResponseDto(
            Floor floor,
            Boolean isMine,
            Boolean hasNewComment,
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
        this.hasNewComment = hasNewComment;
        this.pictures = pictures;
    }
}