package com.curator.oeuvre.controller;

import com.curator.oeuvre.config.CommonResponse;
import com.curator.oeuvre.domain.User;
import com.curator.oeuvre.dto.common.response.PageResponseDto;
import com.curator.oeuvre.dto.notification.response.GetNotificationResponseDto;
import com.curator.oeuvre.service.NotificationService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/pictures")
@Api(tags = "08. 알림 🔔")
@RequiredArgsConstructor
@Validated
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping
    @Operation(summary = "알림 전체 조회", description = "접근 유저의 알림을 전체 조회하는 API 입니다.")
    public CommonResponse<PageResponseDto<List<GetNotificationResponseDto>>> getNotifications(@AuthenticationPrincipal User authUser,
                                                                                           @Parameter(description = "페이지", example = "0") @RequestParam(required = true) @Min(value = 0) Integer page,
                                                                                           @Parameter(description = "페이지 사이즈", example = "10") @RequestParam(required = true) @Min(value = 10) @Max(value = 50) Integer size) {

        log.info("get-notifications");
        log.info("api = 알림 전체 조회, user = {}", authUser.getNo());

        PageResponseDto<List<GetNotificationResponseDto>> result = notificationService.getNotifications(authUser, page, size);
        return CommonResponse.onSuccess(result);
    }

}