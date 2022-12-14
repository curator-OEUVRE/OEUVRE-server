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
@Api(tags = "04. ์ฌ์ง ๐")
@RequiredArgsConstructor
@Validated
public class PictureController {

    private final PictureService pictureService;

    @GetMapping("/{pictureNo}")
    @Operation(summary = "์ฌ์ง ์์ธ ์กฐํ", description = "์ฌ์ง ์์ธ ์กฐํ API ์๋๋ค.")
    public CommonResponse<GetPictureResponseDto> getPicture(@AuthenticationPrincipal User authUser,
                                                            @PathVariable Long pictureNo) {
        log.info("get-picture");
        log.info("api = ์ฌ์ง ์กฐํ, user = {}", authUser.getNo());

        GetPictureResponseDto result = pictureService.getPicture(authUser, pictureNo);
        return CommonResponse.onSuccess(result);
    }

    @PostMapping("/{pictureNo}/like")
    @Operation(summary = "์ฌ์ง ์ข์์", description = "์ฌ์ง ์ข์์ ์์ฑ API ์๋๋ค.")
    public CommonResponse<String> postPictureLike(@AuthenticationPrincipal User authUser,
                                                            @PathVariable Long pictureNo) {
        log.info("post-picture-like");
        log.info("api = ์ฌ์ง ์ข์์, user = {}", authUser.getNo());

        pictureService.postPictureLike(authUser, pictureNo);
        return CommonResponse.onSuccess("์ฌ์ง ์ข์์ ์ฑ๊ณต");
    }

    @DeleteMapping("/{pictureNo}/like")
    @Operation(summary = "์ฌ์ง ์ข์์ ์ทจ์", description = "์ฌ์ง ์ข์์ ์ญ์? API ์๋๋ค.")
    public CommonResponse<String> deletePictureLike(@AuthenticationPrincipal User authUser,
                                                  @PathVariable Long pictureNo) {
        log.info("delete-picture-like");
        log.info("api = ์ฌ์ง ์ข์์ ์ญ์?, user = {}", authUser.getNo());

        pictureService.deletePictureLike(authUser, pictureNo);
        return CommonResponse.onSuccess("์ฌ์ง ์ข์์ ์ทจ์ ์ฑ๊ณต");
    }

    @PostMapping("/{pictureNo}/scrap")
    @Operation(summary = "์ฌ์ง ์คํฌ๋ฉ", description = "์ฌ์ง ์คํฌ๋ฉ ์์ฑ API ์๋๋ค.")
    public CommonResponse<String> postPictureScrap(@AuthenticationPrincipal User authUser,
                                                  @PathVariable Long pictureNo) {
        log.info("post-picture-scrap");
        log.info("api = ์ฌ์ง ์คํฌ๋ฉ, user = {}", authUser.getNo());

        pictureService.postPictureScrap(authUser, pictureNo);
        return CommonResponse.onSuccess("์ฌ์ง ์คํฌ๋ฉ ์ฑ๊ณต");
    }

    @DeleteMapping("/{pictureNo}/scrap")
    @Operation(summary = "์ฌ์ง ์คํฌ๋ฉ ์ทจ์", description = "์ฌ์ง ์คํฌ๋ฉ ์ญ์? API ์๋๋ค.")
    public CommonResponse<String> deletePictureScrap(@AuthenticationPrincipal User authUser,
                                                   @PathVariable Long pictureNo) {
        log.info("delete-picture-scrap");
        log.info("api = ์ฌ์ง ์คํฌ๋ฉ ์ญ์?, user = {}", authUser.getNo());

        pictureService.deletePictureScrap(authUser, pictureNo);
        return CommonResponse.onSuccess("์ฌ์ง ์คํฌ๋ฉ ์ทจ์ ์ฑ๊ณต");
    }

    @GetMapping("/{pictureNo}/like")
    @Operation(summary = "์ฌ์ง ์ข์์ ํ ์?์? ์?์ฒด ์กฐํ", description = "์ฌ์ง ์ข์์ ํ ์?์? ์?์ฒด ์กฐํ API ์๋๋ค.\n์ต๊ทผ์ ์ข์์ ํ ์?์? ์์ผ๋ก ๋ณด์ฌ์ง๋๋ค.")
    public CommonResponse<List<GetPictureLikeUserResponseDto>> getPictureLikeUsers(@PathVariable Long pictureNo) {
        log.info("get-picture-like-users");
        log.info("api = ์ฌ์ง ์ข์์ ํ ์?์? ์?์ฒด ์กฐํ");

        List<GetPictureLikeUserResponseDto> result = pictureService.getPictureLikeUsers(pictureNo);
        return CommonResponse.onSuccess(result);
    }

    @PatchMapping("/{pictureNo}")
    @Operation(summary = "์ฌ์ง ์ค๋ช ์์?", description = "์ฌ์ง ์ค๋ช๊ณผ ํด์ํ๊ทธ ์์? API ์๋๋ค.")
    public CommonResponse<String> patchPictureDescription(@AuthenticationPrincipal User authUser,
                                                          @PathVariable Long pictureNo,
                                                          @Valid @RequestBody PatchPictureRequestDto patchPictureRequestDto, BindingResult bindingResult) {
        log.info("patch-picture-description");
        log.info("api = ์ฌ์ง ์ค๋ช ์์?, user = {}", authUser.getNo());

        if (bindingResult.hasErrors()) {
            ObjectError objectError = bindingResult.getAllErrors().stream().findFirst().get();
            return CommonResponse.onFailure("400", objectError.getDefaultMessage(), null);
        }

        pictureService.patchPictureDescription(authUser, pictureNo, patchPictureRequestDto);
        return CommonResponse.onSuccess("์ฌ์ง ์ค๋ช ์์? ์ฑ๊ณต");
    }

    @DeleteMapping("/{pictureNo}")
    @Operation(summary = "์ฌ์ง ์ญ์?", description = "์ฌ์ง ์ญ์? API ์๋๋ค.\n ์ฌ์ง์ ์ญ์?ํ๋ฉด ํด๋น ์ฌ์ง๋ณด๋ค ๋ท ์์์ ์ฌ์ง๋ค์ด ์์๊ฐ ๋ก๊ฒจ์ง๋๋ค.")
    public CommonResponse<String> deletePicture(@AuthenticationPrincipal User authUser,  @PathVariable Long pictureNo) {
        log.info("delete-picture");
        log.info("api = ์ฌ์ง ์ญ์?, user = {}", authUser.getNo());

        pictureService.deletePicture(authUser, pictureNo);
        return CommonResponse.onSuccess("์ฌ์ง ์ญ์? ์ฑ๊ณต");
    }
}

