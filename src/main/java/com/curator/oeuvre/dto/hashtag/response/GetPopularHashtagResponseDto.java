package com.curator.oeuvre.dto.hashtag.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import java.util.List;

@Getter
@ToString
@EqualsAndHashCode
@ApiModel(value = "#️⃣ 인기 해시태그 조회 API Response")
public class GetPopularHashtagResponseDto {

    @ApiModelProperty(notes = "해시태그 no", example = "1")
    private final Long hashtagNo;

    @ApiModelProperty(notes = "해시태그", example = "#노을")
    private final String hashtag;

    @ApiModelProperty(notes = "사진 목록")
    private final List<GetHashtagPictureDto> pictures;

    public GetPopularHashtagResponseDto (
            Long hashtagNo,
            String hashtag,
            List<GetHashtagPictureDto> pictures
    ) {
        this.hashtagNo = hashtagNo;
        this.hashtag = hashtag;
        this.pictures = pictures;
    }
}
