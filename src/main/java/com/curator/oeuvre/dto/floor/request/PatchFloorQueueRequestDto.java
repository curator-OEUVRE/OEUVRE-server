package com.curator.oeuvre.dto.floor.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel(value = "🎞 플로어 순서 수정 API Request")
public class PatchFloorQueueRequestDto {

    @ApiModelProperty(notes = "플로어 no", example = "1")
    @NotNull(message = "플로어 no를 입력해주세요")
    private Long floorNo;

    @ApiModelProperty(notes = "플로어 순서", example = "1")
    @NotNull(message = "플로어 순서를 입력해주세요")
    private Integer queue;

}

