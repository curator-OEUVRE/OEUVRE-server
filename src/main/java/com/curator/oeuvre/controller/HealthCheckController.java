package com.curator.oeuvre.controller;

import com.curator.oeuvre.config.CommonResponse;
import com.curator.oeuvre.config.ErrorResponse;
import com.curator.oeuvre.constant.ErrorCode;
import com.curator.oeuvre.exception.*;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.rmi.ServerException;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/health-check")
@Api(tags = "00. 테스트 👋")
@RequiredArgsConstructor
public class HealthCheckController {

    @Operation(summary = "테스트 api", description = "테스트용 api 입니덩")
    @GetMapping(produces = "application/json;charset=utf-8")
    public CommonResponse<String> healthCheck () {
        log.info("health-check");
        return CommonResponse.onSuccess("테스트 성공");
    }
}
