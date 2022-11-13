package com.curator.oeuvre.controller;

import com.curator.oeuvre.config.CommonResponse;
import com.curator.oeuvre.domain.User;
import com.curator.oeuvre.dto.common.response.PageResponseDto;
import com.curator.oeuvre.dto.hashtag.response.GetHashtagPictureDto;
import com.curator.oeuvre.dto.hashtag.response.GetHashtagSearchResponseDto;
import com.curator.oeuvre.dto.hashtag.response.GetPopularHashtagResponseDto;
import com.curator.oeuvre.service.HashtagService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/hashtags")
@Api(tags = "05. 해시태그 #️⃣")
@RequiredArgsConstructor
@Validated
public class HashtagController {

    private final HashtagService hashtagService;

    @GetMapping
    @Operation(summary = "해시태그 검색", description = "플로어 생성/편집 시 이용되는 사진 해시 태그 검색 API 입니다.\n" +
                                                    "keyword로 시작하는 hastag들을 태그 수 순으로 size개씩 페이지네이션 해서 보여줍니다.\n" +
                                                    "page는 0부터 시작합니다. size는 10-50 가능합니다. 검색어는 #으로 시작해주세요.")
    public CommonResponse<PageResponseDto<List<GetHashtagSearchResponseDto>>> searchHashtags(
            @Parameter(description = "검색어", example = "#바다") @RequestParam(required = true) String keyword,
            @Parameter(description = "페이지", example = "0") @RequestParam(required = true) @Min(value = 0) Integer page,
            @Parameter(description = "페이지 사이즈", example = "10") @RequestParam(required = true) @Min(value = 10) @Max(value = 50) Integer size) {
        log.info("search-hastags");
        log.info("api = 해시태그 검색");

        PageResponseDto<List<GetHashtagSearchResponseDto>> result = hashtagService.searchHashtags(keyword, page, size);
        return CommonResponse.onSuccess(result);
    }

    @GetMapping("/popular")
    @Operation(summary = "인기 해시태그 조회", description = "탐색 탭 인기 해시태그 조회 API 입니다.\n" +
            "최근 2주간 가장 많이 태그된 해시태그 최대 10개가 보여집니다. 각 해시태그는 사진 최대 7장을 포함합니다.\n" +
            "차단한 유저의 사진은 보이지 않습니다.")
    public CommonResponse<List<GetPopularHashtagResponseDto>> getPopularHashtags(@AuthenticationPrincipal User authUser) {
        log.info("get-popular-hashtags");
        log.info("api = 인기 해시태그 조회");

        List<GetPopularHashtagResponseDto> result = hashtagService.getPopularHashtags(authUser);
        return CommonResponse.onSuccess(result);
    }

    @GetMapping("/{hashtagNo}/pictures")
    @Operation(summary = "해시태그 사진 전체 조회", description = "해당 해시태그의 사진을 전체 조회 하는 API 입니다.\n" +
            "인기순(popular), 최신순(recent)\n" +
            "page는 0부터 시작합니다. size는 10-50 가능합니다. 차단한 유저의 사진은 보이지 않습니다.")
    public CommonResponse<PageResponseDto<List<GetHashtagPictureDto>>> getHashtagPictures(
            @AuthenticationPrincipal User authUser,
            @PathVariable Long hashtagNo,
            @Parameter(description = "정렬 기준", example = "popular") @RequestParam(required = true) String sortBy,
            @Parameter(description = "페이지", example = "0") @RequestParam(required = true) @Min(value = 0) Integer page,
            @Parameter(description = "페이지 사이즈", example = "10") @RequestParam(required = true) @Min(value = 10) @Max(value = 50) Integer size) {
        log.info("get-hashtag-pictures");
        log.info("api = 해시태그 사진 전체 조회");

        PageResponseDto<List<GetHashtagPictureDto>> result = hashtagService.getHashtagPictures(authUser, hashtagNo, sortBy, page, size);
        return CommonResponse.onSuccess(result);
    }
}