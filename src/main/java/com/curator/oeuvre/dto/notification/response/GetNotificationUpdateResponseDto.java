package com.curator.oeuvre.dto.notification.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@ApiModel(value = "๐ ์๋ฆผํญ ์๋ฐ์ดํธ ์ฌ๋ถ ์กฐํ API Response")
public class GetNotificationUpdateResponseDto {

    @ApiModelProperty(notes = "์๋ฐ์ดํธ ์ฌ๋ถ", example = "false")
    private final Boolean isUpdated;

    public GetNotificationUpdateResponseDto (
            Boolean isUpdated
    ) {
        this.isUpdated = isUpdated;
    }
}



