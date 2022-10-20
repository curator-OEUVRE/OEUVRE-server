package com.curator.oeuvre.controller;

import com.curator.oeuvre.config.CommonResponse;
import com.curator.oeuvre.domain.User;
import com.curator.oeuvre.dto.picture.request.PatchPictureRequestDto;
import com.curator.oeuvre.dto.picture.response.GetPictureResponseDto;
import com.curator.oeuvre.dto.picture.response.GetPictureLikeUserResponseDto;
import com.curator.oeuvre.service.PictureService;
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
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/pictures")
@Api(tags = "04. 사진 🌃")
@RequiredArgsConstructor
@Validated
public class PictureController {

    private final PictureService pictureService;

    @GetMapping("/{pictureNo}")
    @Operation(summary = "사진 조회", description = "사진 상세 조회 API 입니다.")
    public CommonResponse<GetPictureResponseDto> getPicture(@AuthenticationPrincipal User authUser,
                                                            @PathVariable Long pictureNo) {
        log.info("get-picture");
        log.info("api = 사진 조회, user = {}", authUser.getNo());

        GetPictureResponseDto result = pictureService.getPicture(authUser, pictureNo);
        return CommonResponse.onSuccess(result);
    }

    @PostMapping("/{pictureNo}/like")
    @Operation(summary = "사진 좋아요", description = "사진 좋아요 생성 API 입니다.")
    public CommonResponse<String> postPictureLike(@AuthenticationPrincipal User authUser,
                                                            @PathVariable Long pictureNo) {
        log.info("post-picture-like");
        log.info("api = 사진 좋아요, user = {}", authUser.getNo());

        pictureService.postPictureLike(authUser, pictureNo);
        return CommonResponse.onSuccess("사진 좋아요 성공");
    }

    @DeleteMapping("/{pictureNo}/like")
    @Operation(summary = "사진 좋아요 취소", description = "사진 좋아요 삭제 API 입니다.")
    public CommonResponse<String> deletePictureLike(@AuthenticationPrincipal User authUser,
                                                  @PathVariable Long pictureNo) {
        log.info("delete-picture-like");
        log.info("api = 사진 좋아요 삭제, user = {}", authUser.getNo());

        pictureService.deletePictureLike(authUser, pictureNo);
        return CommonResponse.onSuccess("사진 좋아요 취소 성공");
    }

    @PostMapping("/{pictureNo}/scrap")
    @Operation(summary = "사진 스크랩", description = "사진 스크랩 생성 API 입니다.")
    public CommonResponse<String> postPictureScrap(@AuthenticationPrincipal User authUser,
                                                  @PathVariable Long pictureNo) {
        log.info("post-picture-scrap");
        log.info("api = 사진 스크랩, user = {}", authUser.getNo());

        pictureService.postPictureScrap(authUser, pictureNo);
        return CommonResponse.onSuccess("사진 스크랩 성공");
    }

    @DeleteMapping("/{pictureNo}/scrap")
    @Operation(summary = "사진 스크랩 취소", description = "사진 스크랩 삭제 API 입니다.")
    public CommonResponse<String> deletePictureScrap(@AuthenticationPrincipal User authUser,
                                                   @PathVariable Long pictureNo) {
        log.info("delete-picture-scrap");
        log.info("api = 사진 스크랩 삭제, user = {}", authUser.getNo());

        pictureService.deletePictureScrap(authUser, pictureNo);
        return CommonResponse.onSuccess("사진 스크랩 취소 성공");
    }

    @GetMapping("/{pictureNo}/like")
    @Operation(summary = "사진 좋아요 한 유저 전체 조회", description = "사진 좋아요 한 유저 전체 조회 API 입니다.\n최근에 좋아요 한 유저 순으로 보여집니다.")
    public CommonResponse<List<GetPictureLikeUserResponseDto>> getPictureLikeUsers(@PathVariable Long pictureNo) {
        log.info("get-picture-like-users");
        log.info("api = 사진 좋아요 한 유저 전체 조회");

        List<GetPictureLikeUserResponseDto> result = pictureService.getPictureLikeUsers(pictureNo);
        return CommonResponse.onSuccess(result);
    }

    @PatchMapping("/{pictureNo}")
    @Operation(summary = "사진 설명 수정", description = "사진 설명과 해시태그 수정 API 입니다.")
    public CommonResponse<String> patchPictureDescription(@AuthenticationPrincipal User authUser,
                                                          @PathVariable Long pictureNo,
                                                          @Valid @RequestBody PatchPictureRequestDto patchPictureRequestDto, BindingResult bindingResult) {
        log.info("patch-picture-description");
        log.info("api = 사진 설명 수정, user = {}", authUser.getNo());

        if (bindingResult.hasErrors()) {
            ObjectError objectError = bindingResult.getAllErrors().stream().findFirst().get();
            return CommonResponse.onFailure("400", objectError.getDefaultMessage(), null);
        }

        pictureService.patchPictureDescription(authUser, pictureNo, patchPictureRequestDto);
        return CommonResponse.onSuccess("사진 설명 수정 성공");
    }


}

