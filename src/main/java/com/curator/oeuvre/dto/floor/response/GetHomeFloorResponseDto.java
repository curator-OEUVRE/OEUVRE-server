package com.curator.oeuvre.dto.floor.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@ApiModel(value = "🎞 홈탭 플로어 전체 조회 API Response")
public class GetHomeFloorResponseDto {

    @ApiModelProperty(notes = "플로어 no", example = "1")
    private final Long floorNo;

    @ApiModelProperty(notes = "플로어 이름", example = "제주 여행 기록")
    private final String floorName;

    @ApiModelProperty(notes = "플로어 층", example = "5")
    private final Integer queue;

    @ApiModelProperty(notes = "전시회 이름", example = "원진이의 전시회")
    private final String exhibitionName;

    @ApiModelProperty(notes = "썸네일 사진 url", example = "image_url")
    private final String thumbnailUrl;

    @ApiModelProperty(notes = "회원 no", example = "1")
    private final Long userNo;

    @ApiModelProperty(notes = "아이디", example = "one_zzini_")
    private final String id;

    @ApiModelProperty(notes = "프로필 이미지 url", example = "image_url")
    private final String profileImageUrl;

    @ApiModelProperty(notes = "새 게시물 여부", example = "true")
    private final Boolean isNew;

    @ApiModelProperty(notes = "업데이트 여부", example = "false")
    private final Boolean isUpdated;

    @ApiModelProperty(notes = "업데이트 사진 수", example = "0")
    private final Integer updateCount;

    @ApiModelProperty(notes = "내 플로어 여부", example = "false")
    private final Boolean isMine;

    @ApiModelProperty(notes = "업데이트 날짜", example = "2022-10-22 16:44:48")
    private final String updatedAt;

    public GetHomeFloorResponseDto(
            Long floorNo,
            String floorName,
            Integer queue,
            String exhibitionName,
            String thumbnailUrl,
            Long userNo,
            String id,
            String profileImageUrl,
            Boolean isNew,
            Boolean isUpdated,
            Integer updateCount,
            Boolean isMine,
            String updatedAt
    ) {
        this.floorNo = floorNo;
        this.floorName = floorName;
        this.queue = queue;
        this.exhibitionName = exhibitionName;
        this.thumbnailUrl = thumbnailUrl;
        this.userNo = userNo;
        this.id = id;
        this.profileImageUrl = profileImageUrl;
        this.isNew = isNew;
        this.isUpdated = isUpdated;
        this.updateCount = updateCount;
        this.isMine = isMine;
        this.updatedAt = updatedAt;
    }
}
