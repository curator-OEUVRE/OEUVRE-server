package com.curator.oeuvre.controller;

import com.curator.oeuvre.config.CommonResponse;
import com.curator.oeuvre.config.ErrorResponse;
import com.curator.oeuvre.dto.oauth.request.LoginRequestDto;
import com.curator.oeuvre.dto.oauth.response.LoginResponseDto;
import com.curator.oeuvre.service.LoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/login")
@Api(tags = "01. 로그인 🔑")
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @PostMapping(value = "/kakao")
    @Operation(summary = "카카오 로그인", description = "카카오 로그인 API 입니다.\n가입되지 않은 유저일 경우 에러와 함께 카카오 이메일을 반환합니다.")
    public CommonResponse<LoginResponseDto> kakaoLogin(@Valid @RequestBody LoginRequestDto loginRequestDto) {
        log.info("kakao-login");

        LoginResponseDto loginResponseDto = loginService.kakoLogin(loginRequestDto);
        return CommonResponse.onSuccess(loginResponseDto);
    }
}
