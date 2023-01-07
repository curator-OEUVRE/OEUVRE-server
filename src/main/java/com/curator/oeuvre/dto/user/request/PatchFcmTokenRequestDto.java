package com.curator.oeuvre.dto.user.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel(value = "👤 fcm 토큰 업데이트 API Request")
public class PatchFcmTokenRequestDto {

    @ApiModelProperty(notes = "fcm 토큰", example = "token")
    @NotNull
    @NotEmpty
    private String token;
}
