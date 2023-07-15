package com.curator.oeuvre.dto.hashtag.response;

import com.curator.oeuvre.domain.Hashtag;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class GetHashtagSearchResponseDto {

    @ApiModelProperty(notes = "해시태그 no", example = "1")
    private final Long hashtagNo;

    @ApiModelProperty(notes = "해시태그", example = "노을")
    private final String hashtag;

    @ApiModelProperty(notes = "태그 수", example = "32")
    private final Long tagCount;


    public GetHashtagSearchResponseDto(Hashtag hashtag) {
        this.hashtagNo = hashtag.getNo();
        this.hashtag = hashtag.getHashtag();
        this.tagCount = hashtag.getTagCount();
    }
}
