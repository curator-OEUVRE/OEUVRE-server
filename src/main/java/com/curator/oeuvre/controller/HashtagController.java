package com.curator.oeuvre.controller;

import com.curator.oeuvre.config.CommonResponse;
import com.curator.oeuvre.domain.User;
import com.curator.oeuvre.dto.hashtag.response.GetHashtagResponseDto;
import com.curator.oeuvre.dto.picture.response.GetPictureResponseDto;
import com.curator.oeuvre.service.HashtagService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    @GetMapping("")
    @Operation(summary = "해시태그 검색", description = "플로어 생성/편집 시 이용되는 사진 해시 태그 검색 API 입니다.\n" +
                                                    "keyword로 시작하는 hastag들을 태그 수 순으로 size개씩 페이지네이션 해서 보여줍니다.")
    public CommonResponse<List<GetHashtagResponseDto>> searchHashtags(
            @Parameter(description = "검색어", example = "바다") @RequestParam(required = false) String keyword,
            @Parameter(description = "페이지", example = "0") @RequestParam(required = true) @Min(value = 0) Integer page,
            @Parameter(description = "페이지 사이즈", example = "10") @RequestParam(required = true) @Min(value = 10) @Max(value = 50) Integer size) {
        log.info("search-hastags");
        log.info("api = 해시태그 검색");

        List<GetHashtagResponseDto> result = hashtagService.searchHashtags(keyword, page, size);
        return CommonResponse.onSuccess(result);
    }
}