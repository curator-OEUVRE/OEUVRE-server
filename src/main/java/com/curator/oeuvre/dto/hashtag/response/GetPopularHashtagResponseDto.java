package com.curator.oeuvre.dto.hashtag.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.util.List;

@Getter
@ToString
@Setter
@EqualsAndHashCode
public class GetPopularHashtagResponseDto {

    @ApiModelProperty(notes = "해시태그 no", example = "1")
    private final Long hashtagNo;

    @ApiModelProperty(notes = "해시태그", example = "#노을")
    private final String hashtag;

    @ApiModelProperty(notes = "고정 여부", example = "true")
    private final Boolean isHead;

    @ApiModelProperty(notes = "사진 목록")
    private final List<GetHashtagPictureDto> pictures;

    public GetPopularHashtagResponseDto (
            Long hashtagNo,
            String hashtag,
            Boolean isHead,
            List<GetHashtagPictureDto> pictures
    ) {
        this.hashtagNo = hashtagNo;
        this.hashtag = hashtag;
        this.isHead = isHead;
        this.pictures = pictures;
    }
}
