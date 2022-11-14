package com.curator.oeuvre.dto.notification.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@ApiModel(value = "🔔 알림탭 업데이트 여부 조회 API Response")
public class GetNotificationUpdateResponseDto {

    @ApiModelProperty(notes = "업데이트 여부", example = "false")
    private final Boolean isUpdated;

    public GetNotificationUpdateResponseDto (
            Boolean isUpdated
    ) {
        this.isUpdated = isUpdated;
    }
}



