package com.curator.oeuvre.controller;

import com.curator.oeuvre.config.CommonResponse;
import com.curator.oeuvre.domain.User;
import com.curator.oeuvre.dto.floor.response.GetFloorResponseDto;
import com.curator.oeuvre.dto.picture.response.GetPictureResponseDto;
import com.curator.oeuvre.service.PictureService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}

