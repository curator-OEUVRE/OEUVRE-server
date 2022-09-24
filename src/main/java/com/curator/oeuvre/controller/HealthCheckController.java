package com.curator.oeuvre.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health-check")
@RequiredArgsConstructor
public class HealthCheckController {

    @Operation(summary = "테스트 api", description = "테스트용 api 입니덩")
    @GetMapping
    public String healthCheck () {
        return "테스트 성공!";
    }
}
