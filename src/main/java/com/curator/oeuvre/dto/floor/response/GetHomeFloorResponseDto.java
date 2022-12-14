package com.curator.oeuvre.dto.floor.response;

import com.curator.oeuvre.domain.Floor;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@ApiModel(value = "π νν­ νλ‘μ΄ μ μ²΄ μ‘°ν API Response")
public class GetHomeFloorResponseDto {

    @ApiModelProperty(notes = "νλ‘μ΄ no", example = "1")
    private final Long floorNo;

    @ApiModelProperty(notes = "νλ‘μ΄ μ΄λ¦", example = "μ μ£Ό μ¬ν κΈ°λ‘")
    private final String floorName;

    @ApiModelProperty(notes = "νλ‘μ΄ μΈ΅", example = "5")
    private final Integer queue;

    @ApiModelProperty(notes = "μ μν μ΄λ¦", example = "μμ§μ΄μ μ μν")
    private final String exhibitionName;

    @ApiModelProperty(notes = "μΈλ€μΌ μ¬μ§ url", example = "image_url")
    private final String thumbnailUrl;

    @ApiModelProperty(notes = "μΈλ‘ κΈΈμ΄", example = "188")
    private final Float height;

    @ApiModelProperty(notes = "κ°λ‘ κΈΈμ΄", example = "70")
    private final Float width;

    @ApiModelProperty(notes = "νμ no", example = "1")
    private final Long userNo;

    @ApiModelProperty(notes = "μμ΄λ", example = "one_zzini_")
    private final String id;

    @ApiModelProperty(notes = "νλ‘ν μ΄λ―Έμ§ url", example = "image_url")
    private final String profileImageUrl;

    @ApiModelProperty(notes = "μ κ²μλ¬Ό μ¬λΆ", example = "true")
    private final Boolean isNew;

    @ApiModelProperty(notes = "μλ°μ΄νΈ μ¬λΆ", example = "false")
    private final Boolean isUpdated;

    @ApiModelProperty(notes = "μλ°μ΄νΈ μ¬μ§ μ", example = "0")
    private final Integer updateCount;

    @ApiModelProperty(notes = "λ΄ νλ‘μ΄ μ¬λΆ", example = "false")
    private final Boolean isMine;

    @ApiModelProperty(notes = "μλ°μ΄νΈ λ μ§", example = "2022-10-22 16:44:48")
    private final String updatedAt;

    public GetHomeFloorResponseDto(
            Long floorNo,
            String floorName,
            Integer queue,
            String exhibitionName,
            String thumbnailUrl,
            Float height,
            Float width,
            Long userNo,
            String id,
            String profileImageUrl,
            Boolean isNew,
            Boolean isUpdated,
            Integer updateCount,
            Boolean isMine,
            String updatedAt
    ) {
        this.floorNo = floorNo;
        this.floorName = floorName;
        this.queue = queue;
        this.exhibitionName = exhibitionName;
        this.thumbnailUrl = thumbnailUrl;
        this.height = height;
        this.width = width;
        this.userNo = userNo;
        this.id = id;
        this.profileImageUrl = profileImageUrl;
        this.isNew = isNew;
        this.isUpdated = isUpdated;
        this.updateCount = updateCount;
        this.isMine = isMine;
        this.updatedAt = updatedAt;
    }
}
