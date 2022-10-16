package com.curator.oeuvre.dto.hashtag.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@Builder
@ApiModel(value = "#️⃣ 해시태그 검색 API Response")
public class GetHashtagResponseDto {

    @ApiModelProperty(notes = "해시태그 no", example = "1")
    private final Long hashtagNo;

    @ApiModelProperty(notes = "해시태그", example = "#노을")
    private final String hashtag;

    @ApiModelProperty(notes = "태그 수", example = "32")
    private final Long tagCount;


    public GetHashtagResponseDto (
            Long hashtagNo,
            String hashtag,
            Long tagCount
    ) {
        this.hashtagNo = hashtagNo;
        this.hashtag = hashtag;
        this.tagCount = tagCount;
    }
}
