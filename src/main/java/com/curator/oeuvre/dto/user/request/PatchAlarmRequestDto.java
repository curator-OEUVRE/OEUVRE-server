package com.curator.oeuvre.dto.user.request;

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
@ApiModel(value = "👤 알람 수신여부 업데이트 API Request")
public class PatchAlarmRequestDto {

    @ApiModelProperty(notes = "isAlarmOn", example = "true")
    @NotNull
    private Boolean isAlarmOn;
}