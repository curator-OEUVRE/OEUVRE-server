package com.curator.oeuvre.controller;

import com.curator.oeuvre.config.CommonResponse;
import com.curator.oeuvre.domain.User;
import com.curator.oeuvre.dto.floor.request.PatchFloorRequestDto;
import com.curator.oeuvre.dto.floor.request.PostFloorRequestDto;
import com.curator.oeuvre.dto.floor.response.GetFloorResponseDto;
import com.curator.oeuvre.dto.floor.response.PostFloorResponseDto;
import com.curator.oeuvre.exception.BadRequestException;
import com.curator.oeuvre.service.FloorService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import static com.curator.oeuvre.constant.ErrorCode.*;

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
        postFloorRequestDto.getPictures().forEach( picture -> {
            if (picture.getImageUrl() == null) throw new BadRequestException(EMPTY_IMAGE_URL);
            if (picture.getQueue() == null) throw new BadRequestException(EMPTY_QUEUE);
            if (picture.getHeight() == null) throw new BadRequestException(EMPTY_HEIGHT);
            if (picture.getLocation() == null ) throw new BadRequestException(EMPTY_LOCATION);
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
        patchFloorRequestDto.getPictures().forEach( picture -> {
            if (picture.getPictureNo() == null) throw new BadRequestException(EMPTY_PICTURE_NO);
            if (picture.getPictureNo() == 0 && picture.getImageUrl() == null) throw new BadRequestException(EMPTY_IMAGE_URL);
            if (picture.getQueue() == null) throw new BadRequestException(EMPTY_QUEUE);
            if (picture.getHeight() == null) throw new BadRequestException(EMPTY_HEIGHT);
            if (picture.getLocation() == null ) throw new BadRequestException(EMPTY_LOCATION);
        });

        floorService.patchFloor(authUser, floorNo, patchFloorRequestDto);
        return CommonResponse.onSuccess("플로어 편집 성공");
    }

}
