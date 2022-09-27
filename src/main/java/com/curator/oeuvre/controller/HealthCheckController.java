package com.curator.oeuvre.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/health-check")
@RequiredArgsConstructor
public class HealthCheckController {

    @Operation(summary = "테스트 api", description = "테스트용 api 입니덩")
    @GetMapping(produces = "application/json;charset=utf-8")
    public String healthCheck () {
        log.info("health-check");
        return "테스트 성공!";
    }
}
