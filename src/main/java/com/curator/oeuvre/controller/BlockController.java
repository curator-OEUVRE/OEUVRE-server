package com.curator.oeuvre.controller;

import com.curator.oeuvre.config.CommonResponse;
import com.curator.oeuvre.domain.User;
import com.curator.oeuvre.service.BlockService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/blocks")
@Api(tags = "07. 차단 ❌")
@RequiredArgsConstructor
@Validated
public class BlockController {

    private final BlockService blockService;

    @PostMapping("/user/{userNo}")
    @Operation(summary = "회원 차단", description = "회원 차단 API 입니다.\n회원 차단 시 자동으로 팔로잉 취소 됩니다.")
    public CommonResponse<String> postUserBlock(@AuthenticationPrincipal User authUser,
                                                   @PathVariable Long userNo) {
        log.info("post-user-block");
        log.info("api = 회원 차단, user = {}", authUser.getNo());

        blockService.postUserBlock(authUser, userNo);
        return CommonResponse.onSuccess("회원 차단 성공");
    }

    @DeleteMapping("/user/{userNo}")
    @Operation(summary = "회원 차단 해제", description = "회원 차단 해제 API 입니다.")
    public CommonResponse<String> deleteUserBlock(@AuthenticationPrincipal User authUser,
                                                @PathVariable Long userNo) {
        log.info("delete-user-block");
        log.info("api = 회원 차단 해제, user = {}", authUser.getNo());

        blockService.deleteUserBlock(authUser, userNo);
        return CommonResponse.onSuccess("회원 차단 해제 성공");
    }
}