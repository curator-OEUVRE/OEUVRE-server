package com.curator.oeuvre.controller;

import com.curator.oeuvre.config.CommonResponse;
import com.curator.oeuvre.domain.User;
import com.curator.oeuvre.dto.common.response.PageResponseDto;
import com.curator.oeuvre.dto.floor.request.PatchFloorQueueRequestDto;
import com.curator.oeuvre.dto.floor.request.PatchFloorRequestDto;
import com.curator.oeuvre.dto.floor.request.PostFloorRequestDto;
import com.curator.oeuvre.dto.floor.response.GetFloorResponseDto;
import com.curator.oeuvre.dto.floor.response.GetFloorSearchResponseDto;
import com.curator.oeuvre.dto.floor.response.GetHomeFloorResponseDto;
import com.curator.oeuvre.dto.floor.response.PostFloorResponseDto;
import com.curator.oeuvre.exception.BadRequestException;
import com.curator.oeuvre.service.FloorService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import java.util.ArrayList;
import java.util.List;

import static com.curator.oeuvre.constant.ErrorCode.*;
import static java.util.Arrays.asList;

@RestController
@Slf4j
@RequestMapping("/floors")
@Api(tags = "03. 플로어 🎞")
@RequiredArgsConstructor
@Validated
public class FloorController {

    private final FloorService floorService;

    @PostMapping
    @Operation(summary = "플로어 생성", description = "플로어 생성 API 입니다.\n플로어에 포함된 사진들, 사진에 포함된 해시태그들 모두 포함해서 새롭게 생성합니다.")
    public CommonResponse<PostFloorResponseDto> postFloor(@AuthenticationPrincipal User authUser,
                                                          @Valid @RequestBody PostFloorRequestDto postFloorRequestDto, BindingResult bindingResult) {
        log.info("post-floor");
        log.info("api = 플로어 생성, user = {}", authUser.getNo());

        if (bindingResult.hasErrors()) {
            ObjectError objectError = bindingResult.getAllErrors().stream().findFirst().get();
            return CommonResponse.onFailure("400", objectError.getDefaultMessage(), null);
        }
        List<String> align = asList("CENTER", "TOP", "BOTTOM");
        if (!align.contains(postFloorRequestDto.getAlignment())) {
            throw new BadRequestException(INVALID_ALIGNMENT);
        }
        List<String> gradient = asList("FULL", "TOP", "BOTTOM");
        if (!gradient.contains(postFloorRequestDto.getGradient())) {
            throw new BadRequestException(INVALID_GRADIENT);
        }
        postFloorRequestDto.getPictures().forEach( picture -> {
            if (picture.getImageUrl().isEmpty() || picture.getImageUrl() == null) throw new BadRequestException(EMPTY_IMAGE_URL);
            if (picture.getQueue() == null) throw new BadRequestException(EMPTY_QUEUE);
            if (picture.getHeight() == null) throw new BadRequestException(EMPTY_HEIGHT);
            if (picture.getWidth() == null ) throw new BadRequestException(EMPTY_WIDTH);
        });
        PostFloorResponseDto result = floorService.postFloor(authUser, postFloorRequestDto);
        return CommonResponse.onSuccess(result);
    }

    @GetMapping("/{floorNo}")
    @Operation(summary = "플로어 조회", description = "플로어 조회 API 입니다. \n해당 플로어의 정보와 사진들을 조회 합니다.")
    public CommonResponse<GetFloorResponseDto> getFloor(@AuthenticationPrincipal User authUser,
                                                        @PathVariable Long floorNo) {
        log.info("get-floor");
        log.info("api = 플로어 조회, user = {}", authUser.getNo());

        GetFloorResponseDto result = floorService.getFloor(authUser, floorNo);
        return CommonResponse.onSuccess(result);
    }

    @PatchMapping("/{floorNo}")
    @Operation(summary = "플로어 편집", description = "플로어 편집 API 입니다.\n플로어 정보, 플로어에 포함된 사진들의 정보, 사진에 포함된 해시태그들의 변경 내용을 업데이트 합니다.\n새로 추가된 사진은 no를 0으로 보내주세요.")
    public CommonResponse<String> patchFloor(@AuthenticationPrincipal User authUser,  @PathVariable Long floorNo,
                                             @Valid @RequestBody PatchFloorRequestDto patchFloorRequestDto, BindingResult bindingResult) {
        log.info("patch-floor");
        log.info("api = 플로어 편집, user = {}", authUser.getNo());

        if (bindingResult.hasErrors()) {
            ObjectError objectError = bindingResult.getAllErrors().stream().findFirst().get();
            return CommonResponse.onFailure("400", objectError.getDefaultMessage(), null);
        }
        List<String> align = asList("CENTER", "TOP", "BOTTOM");
        if (!align.contains(patchFloorRequestDto.getAlignment())) {
            throw new BadRequestException(INVALID_ALIGNMENT);
        }
        List<String> gradient = asList("FULL", "TOP", "BOTTOM");
        if (!gradient.contains(patchFloorRequestDto.getGradient())) {
            throw new BadRequestException(INVALID_GRADIENT);
        }
        patchFloorRequestDto.getPictures().forEach(picture -> {
            if (picture.getPictureNo() == null) throw new BadRequestException(EMPTY_PICTURE_NO);
            if (picture.getPictureNo() == 0 && (picture.getImageUrl().isEmpty() || picture.getImageUrl() == null))
                throw new BadRequestException(EMPTY_IMAGE_URL);
            if (picture.getQueue() == null) throw new BadRequestException(EMPTY_QUEUE);
            if (picture.getHeight() == null) throw new BadRequestException(EMPTY_HEIGHT);
            if (picture.getWidth() == null) throw new BadRequestException(EMPTY_WIDTH);
        });

        floorService.patchFloor(authUser, floorNo, patchFloorRequestDto);
        return CommonResponse.onSuccess("플로어 편집 성공");
    }

    @PatchMapping
    @Operation(summary = "플로어 순서 편집", description = "플로어 순서 편집 API 입니다.")
    public CommonResponse<String> patchFloorQueue(@AuthenticationPrincipal User authUser,
                                                  @Valid @RequestBody List<PatchFloorQueueRequestDto> patchFloorQueueRequestDtos, BindingResult bindingResult) {
        log.info("patch-floor-queue");
        log.info("api = 플로어 순서 편집, user = {}", authUser.getNo());

        if (bindingResult.hasErrors()) {
            ObjectError objectError = bindingResult.getAllErrors().stream().findFirst().get();
            return CommonResponse.onFailure("400", objectError.getDefaultMessage(), null);
        }

        floorService.patchFloorQueue(authUser, patchFloorQueueRequestDtos);
        return CommonResponse.onSuccess("플로어 순서 편집 성공");
    }

    @GetMapping("/home")
    @Operation(summary = "홈탭 플로어 전체 조회", description = "홈탭 플로어 전체 조회 API 입니다. \n접근 유저에 따라 업데이트 여부가 다르게 조회 됩니다.")
    public CommonResponse<PageResponseDto<List<GetHomeFloorResponseDto>>> getHomeFloors(
            @AuthenticationPrincipal User authUser,
            @Parameter(description = "페이지", example = "0") @RequestParam(required = true) @Min(value = 0) Integer page,
            @Parameter(description = "페이지 사이즈", example = "10") @RequestParam(required = true) @Min(value = 10) @Max(value = 50) Integer size) {
        log.info("get-home-floors");
        log.info("api = 홈탭 플로어 전체 조회, user = {}", authUser.getNo());

        PageResponseDto<List<GetHomeFloorResponseDto>> result = floorService.getHomeFloors(authUser, page, size);
        return CommonResponse.onSuccess(result);
    }

    @GetMapping
    @Operation(summary = "플로어 검색", description = "탐색탭 플로어 검색 API 입니다.\n" +
            "keyword를 포함하는 전시회 이름, 플로어 이름을 가진 플로어들을 size개씩 페이지네이션 해서 보여줍니다.\n" +
            "page는 0부터 시작합니다. size는 10-50 가능합니다.")
    public CommonResponse<PageResponseDto<List<GetFloorSearchResponseDto>>> searchFloors(
            @AuthenticationPrincipal User authUser,
            @Parameter(description = "검색어", example = "미국") @RequestParam(required = true) String keyword,
            @Parameter(description = "페이지", example = "0") @RequestParam(required = true) @Min(value = 0) Integer page,
            @Parameter(description = "페이지 사이즈", example = "10") @RequestParam(required = true) @Min(value = 10) @Max(value = 50) Integer size) {
        log.info("search-floors");
        log.info("api = 플로어 검색");

        PageResponseDto<List<GetFloorSearchResponseDto>> result = floorService.searchFloors(authUser, keyword, page, size);
        return CommonResponse.onSuccess(result);
    }

    @DeleteMapping("/{floorNo}")
    @Operation(summary = "플로어 삭제", description = "플로어 삭제 API 입니다.\n 플로어를 삭제하면 해당 플로어 보다 위에 층의 존재하던 플로어들이 한층씩 내려옵니다.")
    public CommonResponse<String> deleteFloor(@AuthenticationPrincipal User authUser,  @PathVariable Long floorNo) {
        log.info("delete-floor");
        log.info("api = 플로어 삭제, user = {}", authUser.getNo());

        floorService.deleteFloor(authUser, floorNo);
        return CommonResponse.onSuccess("플로어 삭제 성공");
    }
}
