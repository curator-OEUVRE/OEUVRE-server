package com.curator.oeuvre.controller;

import com.curator.oeuvre.config.CommonResponse;
import com.curator.oeuvre.domain.User;
import com.curator.oeuvre.dto.common.response.PageResponseDto;
import com.curator.oeuvre.dto.notification.response.GetNotificationResponseDto;
import com.curator.oeuvre.dto.notification.response.GetNotificationUpdateResponseDto;
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
@Api(tags = "08. μλ¦Ό π")
@RequiredArgsConstructor
@Validated
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping
    @Operation(summary = "μλ¦Ό μ μ²΄ μ‘°ν", description = "μ κ·Ό μ μ μ μλ¦Όμ μ μ²΄ μ‘°ννλ API μλλ€.")
    public CommonResponse<PageResponseDto<List<GetNotificationResponseDto>>> getNotifications(@AuthenticationPrincipal User authUser,
                                                                                           @Parameter(description = "νμ΄μ§", example = "0") @RequestParam(required = true) @Min(value = 0) Integer page,
                                                                                           @Parameter(description = "νμ΄μ§ μ¬μ΄μ¦", example = "10") @RequestParam(required = true) @Min(value = 10) @Max(value = 50) Integer size) {

        log.info("get-notifications");
        log.info("api = μλ¦Ό μ μ²΄ μ‘°ν, user = {}", authUser.getNo());

        PageResponseDto<List<GetNotificationResponseDto>> result = notificationService.getNotifications(authUser, page, size);
        return CommonResponse.onSuccess(result);
    }

    @GetMapping("/update-check")
    @Operation(summary = "μλ¦Όν­ μλ°μ΄νΈ μ¬λΆ μ‘°ν", description = "μ κ·Ό μ μ μ μλ¦Όν­ μλ°μ΄νΈ μ¬λΆλ₯Ό μ‘°ννλ API μλλ€.")
    public CommonResponse<GetNotificationUpdateResponseDto> getNotifications(@AuthenticationPrincipal User authUser) {
        log.info("get-notifications-update-check");
        log.info("api = μλ¦Όν­ μλ°μ΄νΈ μ¬λΆ μ‘°ν, user = {}", authUser.getNo());

        GetNotificationUpdateResponseDto result = new GetNotificationUpdateResponseDto(authUser.getIsNotificationUpdated());
        return CommonResponse.onSuccess(result);
    }

}