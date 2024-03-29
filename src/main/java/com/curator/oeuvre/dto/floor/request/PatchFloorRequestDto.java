package com.curator.oeuvre.dto.floor.request;

import com.google.firebase.database.annotations.Nullable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel(value = "🎞 플로어 수정 API Request")
public class PatchFloorRequestDto {

    @ApiModelProperty(notes = "플로어 이름", example = "제주 여행 기록")
    @NotNull
    @NotEmpty
    @Length(max = 10)
    private String name;

    @ApiModelProperty(notes = "플로어 설명", example = "플로어 설명을 써주세요")
    @Length(max = 50)
    private String description;

    @ApiModelProperty(notes = "배경 색상코드", example = "#FFFFFF")
    @NotNull
    @NotEmpty
    private String color;

    @ApiModelProperty(notes = "배경 그라데이션", example = "FULL")
    @NotNull
    @NotEmpty
    private String gradient;

    @ApiModelProperty(notes = "배경 질감", example = "0")
    @Nullable
    private Integer texture;

    @ApiModelProperty(notes = "정렬", example = "CENTER/TOP/BOTTOM")
    @NotNull
    @NotEmpty
    private String alignment;

    @ApiModelProperty(notes = "액자 여부", example = "false")
    @NotNull
    private Boolean isFramed;

    @ApiModelProperty(notes = "공개 여부", example = "true")
    @NotNull
    private Boolean isPublic;

    @ApiModelProperty(notes = "방명록 허용 여부", example = "true")
    @NotNull
    private Boolean isCommentAvailable;

    @ApiModelProperty(notes = "썸네일 사진 no", example = "31")
    @NotNull
    private Long thumbnailNo;

    @ApiModelProperty(notes = "사진 목록")
    @NotNull
    private List<PatchFloorPictureDto> pictures;

}
