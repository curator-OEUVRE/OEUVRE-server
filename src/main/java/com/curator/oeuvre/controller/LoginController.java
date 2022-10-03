package com.curator.oeuvre.controller;

import com.curator.oeuvre.config.CommonResponse;
import com.curator.oeuvre.domain.User;
import com.curator.oeuvre.dto.oauth.request.LoginRequestDto;
import com.curator.oeuvre.dto.oauth.response.LoginResponseDto;
import com.curator.oeuvre.exception.BaseException;
import com.curator.oeuvre.service.JwtServiceImpl;
import com.curator.oeuvre.service.LoginService;
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
import java.io.IOException;
import static com.curator.oeuvre.constant.ErrorCode.GOOGLE_BAD_REQUEST;

@RestController
@Slf4j
@RequestMapping("/login")
@Api(tags = "01. 로그인 🔑")
@RequiredArgsConstructor
@Validated
public class LoginController {

    private final LoginService loginService;
    private final JwtServiceImpl jwtService;

    @PostMapping(value = "/kakao")
    @Operation(summary = "카카오 로그인", description = "카카오 로그인 API 입니다.\n가입되지 않은 유저일 경우 에러와 함께 카카오 이메일을 반환합니다.")
    public CommonResponse<LoginResponseDto> kakaoLogin(@Valid @RequestBody LoginRequestDto loginRequestDto, BindingResult bindingResult) {
        log.info("kakao-login");

        if (bindingResult.hasErrors()) {
            ObjectError objectError = bindingResult.getAllErrors().stream().findFirst().get();
            return CommonResponse.onFailure("400", objectError.getDefaultMessage(), null);
        }

        LoginResponseDto loginResponseDto = loginService.kakaoLogin(loginRequestDto);
        return CommonResponse.onSuccess(loginResponseDto);
    }

    @PostMapping(value = "/google")
    @Operation(summary = "구글 로그인", description = "구글 로그인 API 입니다.\n가입되지 않은 유저일 경우 에러와 함께 구글 이메일을 반환합니다.")
    public CommonResponse<LoginResponseDto> googleLogin(@Valid @RequestBody LoginRequestDto loginRequestDto, BindingResult bindingResult) {
        log.info("google-login");

        LoginResponseDto loginResponseDto;

        if (bindingResult.hasErrors()) {
            ObjectError objectError = bindingResult.getAllErrors().stream().findFirst().get();
            return CommonResponse.onFailure("400", objectError.getDefaultMessage(), null);
        }

        try {
            loginResponseDto = loginService.googleLogin(loginRequestDto);
        } catch (IOException e) {
            throw new BaseException(GOOGLE_BAD_REQUEST);
        }
        return CommonResponse.onSuccess(loginResponseDto);
    }

    @PatchMapping(value = "/refresh")
    @Operation(summary = "토큰 리프레시 (자동 로그인)", description = "자동 로그인 API 입니다.\n리프레시 토큰을 통해 유저의 jwt를 갱신합니다.")
    @ResponseBody
    public CommonResponse<LoginResponseDto> patchRefreshToken(@AuthenticationPrincipal User authUser) {

        log.info("api = 토큰 리프레시, user = {}", authUser.getNo());

        LoginResponseDto loginResponseDto = loginService.updateUserToken(authUser);

        return CommonResponse.onSuccess(loginResponseDto);
    }
}
