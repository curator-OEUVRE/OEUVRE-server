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

import java.util.List;

import static com.curator.oeuvre.constant.ErrorCode.*;

@RestController
@Slf4j
@RequestMapping("/floors")
@Api(tags = "03. νλ‘μ΄ π")
@RequiredArgsConstructor
@Validated
public class FloorController {

    private final FloorService floorService;

    @PostMapping
    @Operation(summary = "νλ‘μ΄ μμ±", description = "νλ‘μ΄ μμ± API μλλ€.\nνλ‘μ΄μ ν¬ν¨λ μ¬μ§λ€, μ¬μ§μ ν¬ν¨λ ν΄μνκ·Έλ€ λͺ¨λ ν¬ν¨ν΄μ μλ‘­κ² μμ±ν©λλ€.")
    public CommonResponse<PostFloorResponseDto> postFloor(@AuthenticationPrincipal User authUser,
                                                          @Valid @RequestBody PostFloorRequestDto postFloorRequestDto, BindingResult bindingResult) {
        log.info("post-floor");
        log.info("api = νλ‘μ΄ μμ±, user = {}", authUser.getNo());

        if (bindingResult.hasErrors()) {
            ObjectError objectError = bindingResult.getAllErrors().stream().findFirst().get();
            return CommonResponse.onFailure("400", objectError.getDefaultMessage(), null);
        }
        postFloorRequestDto.getPictures().forEach( picture -> {
            if (picture.getImageUrl().isEmpty() || picture.getImageUrl() == null) throw new BadRequestException(EMPTY_IMAGE_URL);
            if (picture.getQueue() == null) throw new BadRequestException(EMPTY_QUEUE);
            if (picture.getHeight() == null) throw new BadRequestException(EMPTY_HEIGHT);
            if (picture.getLocation() == null ) throw new BadRequestException(EMPTY_LOCATION);
        });
        PostFloorResponseDto result = floorService.postFloor(authUser, postFloorRequestDto);
        return CommonResponse.onSuccess(result);
    }

    @GetMapping("/{floorNo}")
    @Operation(summary = "νλ‘μ΄ μ‘°ν", description = "νλ‘μ΄ μ‘°ν API μλλ€. \nν΄λΉ νλ‘μ΄μ μ λ³΄μ μ¬μ§λ€μ μ‘°ν ν©λλ€.")
    public CommonResponse<GetFloorResponseDto> getFloor(@AuthenticationPrincipal User authUser,
                                                        @PathVariable Long floorNo) {
        log.info("get-floor");
        log.info("api = νλ‘μ΄ μ‘°ν, user = {}", authUser.getNo());

        GetFloorResponseDto result = floorService.getFloor(authUser, floorNo);
        return CommonResponse.onSuccess(result);
    }

    @PatchMapping("/{floorNo}")
    @Operation(summary = "νλ‘μ΄ νΈμ§", description = "νλ‘μ΄ νΈμ§ API μλλ€.\nνλ‘μ΄ μ λ³΄, νλ‘μ΄μ ν¬ν¨λ μ¬μ§λ€μ μ λ³΄, μ¬μ§μ ν¬ν¨λ ν΄μνκ·Έλ€μ λ³κ²½ λ΄μ©μ μλ°μ΄νΈ ν©λλ€.\nμλ‘ μΆκ°λ μ¬μ§μ noλ₯Ό 0μΌλ‘ λ³΄λ΄μ£ΌμΈμ.")
    public CommonResponse<String> patchFloor(@AuthenticationPrincipal User authUser,  @PathVariable Long floorNo,
                                             @Valid @RequestBody PatchFloorRequestDto patchFloorRequestDto, BindingResult bindingResult) {
        log.info("patch-floor");
        log.info("api = νλ‘μ΄ νΈμ§, user = {}", authUser.getNo());

        if (bindingResult.hasErrors()) {
            ObjectError objectError = bindingResult.getAllErrors().stream().findFirst().get();
            return CommonResponse.onFailure("400", objectError.getDefaultMessage(), null);
        }
        patchFloorRequestDto.getPictures().forEach(picture -> {
            if (picture.getPictureNo() == null) throw new BadRequestException(EMPTY_PICTURE_NO);
            if (picture.getPictureNo() == 0 && (picture.getImageUrl().isEmpty() || picture.getImageUrl() == null))
                throw new BadRequestException(EMPTY_IMAGE_URL);
            if (picture.getQueue() == null) throw new BadRequestException(EMPTY_QUEUE);
            if (picture.getHeight() == null) throw new BadRequestException(EMPTY_HEIGHT);
            if (picture.getLocation() == null) throw new BadRequestException(EMPTY_LOCATION);
        });

        floorService.patchFloor(authUser, floorNo, patchFloorRequestDto);
        return CommonResponse.onSuccess("νλ‘μ΄ νΈμ§ μ±κ³΅");
    }

    @PatchMapping
    @Operation(summary = "νλ‘μ΄ μμ νΈμ§", description = "νλ‘μ΄ μμ νΈμ§ API μλλ€.")
    public CommonResponse<String> patchFloorQueue(@AuthenticationPrincipal User authUser,
                                                  @Valid @RequestBody List<PatchFloorQueueRequestDto> patchFloorQueueRequestDtos, BindingResult bindingResult) {
        log.info("patch-floor-queue");
        log.info("api = νλ‘μ΄ μμ νΈμ§, user = {}", authUser.getNo());

        if (bindingResult.hasErrors()) {
            ObjectError objectError = bindingResult.getAllErrors().stream().findFirst().get();
            return CommonResponse.onFailure("400", objectError.getDefaultMessage(), null);
        }

        floorService.patchFloorQueue(authUser, patchFloorQueueRequestDtos);
        return CommonResponse.onSuccess("νλ‘μ΄ μμ νΈμ§ μ±κ³΅");
    }

    @GetMapping("/home")
    @Operation(summary = "νν­ νλ‘μ΄ μ μ²΄ μ‘°ν", description = "νν­ νλ‘μ΄ μ μ²΄ μ‘°ν API μλλ€. \nμ κ·Ό μ μ μ λ°λΌ μλ°μ΄νΈ μ¬λΆκ° λ€λ₯΄κ² μ‘°ν λ©λλ€.")
    public CommonResponse<PageResponseDto<List<GetHomeFloorResponseDto>>> getHomeFloors(
            @AuthenticationPrincipal User authUser,
            @Parameter(description = "νμ΄μ§", example = "0") @RequestParam(required = true) @Min(value = 0) Integer page,
            @Parameter(description = "νμ΄μ§ μ¬μ΄μ¦", example = "10") @RequestParam(required = true) @Min(value = 10) @Max(value = 50) Integer size) {
        log.info("get-home-floors");
        log.info("api = νν­ νλ‘μ΄ μ μ²΄ μ‘°ν, user = {}", authUser.getNo());

        PageResponseDto<List<GetHomeFloorResponseDto>> result = floorService.getHomeFloors(authUser, page, size);
        return CommonResponse.onSuccess(result);
    }

    @GetMapping
    @Operation(summary = "νλ‘μ΄ κ²μ", description = "νμν­ νλ‘μ΄ κ²μ API μλλ€.\n" +
            "keywordλ₯Ό ν¬ν¨νλ μ μν μ΄λ¦, νλ‘μ΄ μ΄λ¦μ κ°μ§ νλ‘μ΄λ€μ sizeκ°μ© νμ΄μ§λ€μ΄μ ν΄μ λ³΄μ¬μ€λλ€.\n" +
            "pageλ 0λΆν° μμν©λλ€. sizeλ 10-50 κ°λ₯ν©λλ€.")
    public CommonResponse<PageResponseDto<List<GetFloorSearchResponseDto>>> searchFloors(
            @AuthenticationPrincipal User authUser,
            @Parameter(description = "κ²μμ΄", example = "λ―Έκ΅­") @RequestParam(required = true) String keyword,
            @Parameter(description = "νμ΄μ§", example = "0") @RequestParam(required = true) @Min(value = 0) Integer page,
            @Parameter(description = "νμ΄μ§ μ¬μ΄μ¦", example = "10") @RequestParam(required = true) @Min(value = 10) @Max(value = 50) Integer size) {
        log.info("search-floors");
        log.info("api = νλ‘μ΄ κ²μ");

        PageResponseDto<List<GetFloorSearchResponseDto>> result = floorService.searchFloors(authUser, keyword, page, size);
        return CommonResponse.onSuccess(result);
    }

    @DeleteMapping("/{floorNo}")
    @Operation(summary = "νλ‘μ΄ μ­μ ", description = "νλ‘μ΄ μ­μ  API μλλ€.\n νλ‘μ΄λ₯Ό μ­μ νλ©΄ ν΄λΉ νλ‘μ΄ λ³΄λ€ μμ μΈ΅μ μ‘΄μ¬νλ νλ‘μ΄λ€μ΄ νμΈ΅μ© λ΄λ €μ΅λλ€.")
    public CommonResponse<String> deleteFloor(@AuthenticationPrincipal User authUser,  @PathVariable Long floorNo) {
        log.info("delete-floor");
        log.info("api = νλ‘μ΄ μ­μ , user = {}", authUser.getNo());

        floorService.deleteFloor(authUser, floorNo);
        return CommonResponse.onSuccess("νλ‘μ΄ μ­μ  μ±κ³΅");
    }
}
