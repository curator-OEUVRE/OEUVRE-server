package com.curator.oeuvre.controller;

import com.curator.oeuvre.config.CommonResponse;
import com.curator.oeuvre.domain.User;
import com.curator.oeuvre.dto.oauth.request.LoginRequestDto;
import com.curator.oeuvre.dto.oauth.response.LoginResponseDto;
import com.curator.oeuvre.dto.user.response.GetUserProfileResponseDto;
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
@Api(tags = "01. ๋ก๊ทธ์ธ ๐")
@RequiredArgsConstructor
@Validated
public class LoginController {

    private final LoginService loginService;

    @PostMapping(value = "/kakao")
    @Operation(summary = "์นด์นด์ค ๋ก๊ทธ์ธ", description = "์นด์นด์ค ๋ก๊ทธ์ธ API ์๋๋ค.\n๊ฐ์๋์ง ์์ ์ ์ ์ผ ๊ฒฝ์ฐ ์๋ฌ์ ํจ๊ป ์นด์นด์ค ์ด๋ฉ์ผ์ ๋ฐํํฉ๋๋ค.")
    public CommonResponse<LoginResponseDto> kakaoLogin(@Valid @RequestBody LoginRequestDto loginRequestDto, BindingResult bindingResult) {
        log.info("kakao-login");

        if (bindingResult.hasErrors()) {
            ObjectError objectError = bindingResult.getAllErrors().stream().findFirst().get();
            return CommonResponse.onFailure("400", objectError.getDefaultMessage(), null);
        }

        LoginResponseDto result = loginService.kakaoLogin(loginRequestDto);
        return CommonResponse.onSuccess(result);
    }

    @PostMapping(value = "/google")
    @Operation(summary = "๊ตฌ๊ธ ๋ก๊ทธ์ธ", description = "๊ตฌ๊ธ ๋ก๊ทธ์ธ API ์๋๋ค.\n๊ฐ์๋์ง ์์ ์ ์ ์ผ ๊ฒฝ์ฐ ์๋ฌ์ ํจ๊ป ๊ตฌ๊ธ ์ด๋ฉ์ผ์ ๋ฐํํฉ๋๋ค.")
    public CommonResponse<LoginResponseDto> googleLogin(@Valid @RequestBody LoginRequestDto loginRequestDto, BindingResult bindingResult) {
        log.info("google-login");

        LoginResponseDto result;

        if (bindingResult.hasErrors()) {
            ObjectError objectError = bindingResult.getAllErrors().stream().findFirst().get();
            return CommonResponse.onFailure("400", objectError.getDefaultMessage(), null);
        }

        try {
            result = loginService.googleLogin(loginRequestDto);
        } catch (IOException e) {
            throw new BaseException(GOOGLE_BAD_REQUEST);
        }
        return CommonResponse.onSuccess(result);
    }

    @PostMapping(value = "/apple")
    @Operation(summary = "์ ํ ๋ก๊ทธ์ธ", description = "์ ํ ๋ก๊ทธ์ธ API ์๋๋ค.\n๊ฐ์๋์ง ์์ ์ ์ ์ผ ๊ฒฝ์ฐ ์๋ฌ์ ํจ๊ป ์ ํ ์ด๋ฉ์ผ์ ๋ฐํํฉ๋๋ค.")
    public CommonResponse<LoginResponseDto> appleLogin(@Valid @RequestBody LoginRequestDto loginRequestDto, BindingResult bindingResult) {
        log.info("apple-login");

        LoginResponseDto result;

        if (bindingResult.hasErrors()) {
            ObjectError objectError = bindingResult.getAllErrors().stream().findFirst().get();
            return CommonResponse.onFailure("400", objectError.getDefaultMessage(), null);
        }

        result = loginService.appleLogin(loginRequestDto);
        return CommonResponse.onSuccess(result);
    }

    @PatchMapping(value = "/refresh")
    @Operation(summary = "ํ ํฐ ๋ฆฌํ๋ ์ (์๋ ๋ก๊ทธ์ธ)", description = "์๋ ๋ก๊ทธ์ธ API ์๋๋ค.\n๋ฆฌํ๋ ์ ํ ํฐ์ ํตํด ์ ์ ์ jwt๋ฅผ ๊ฐฑ์ ํฉ๋๋ค.")
    @ResponseBody
    public CommonResponse<LoginResponseDto> patchRefreshToken(@AuthenticationPrincipal User authUser) {

        log.info("api = ํ ํฐ ๋ฆฌํ๋ ์, user = {}", authUser.getNo());

        LoginResponseDto result= loginService.updateUserToken(authUser);
        return CommonResponse.onSuccess(result);
    }

    @GetMapping(value = "/check/guest")
    @Operation(summary = "๊ฒ์คํธ ๋ก๊ทธ์ธ ๊ฐ๋ฅ ์ฌ๋ถ ์กฐํ", description = "๊ฒ์คํธ ๋ก๊ทธ์ธ ๊ฐ๋ฅ ์ฌ๋ถ๋ฅผ ์กฐํํ๋ API ์๋๋ค.")
    public CommonResponse<Boolean> getIsGuestLoginAvailable() {

        log.info("get-is-guest-login-available");
        log.info("api = ๊ฒ์คํธ ๋ก๊ทธ์ธ ๊ฐ๋ฅ ์ฌ๋ถ ์กฐํ");

        Boolean result = loginService.getIsGuestLoginAvailable();
        return CommonResponse.onSuccess(result);
    }

    @PostMapping(value = "/guest")
    @Operation(summary = "๊ฒ์คํธ ๋ก๊ทธ์ธ", description = "๊ฒ์คํธ ๋ก๊ทธ์ธ API ์๋๋ค.")
    public CommonResponse<LoginResponseDto> guestLogin() {
        log.info("guest-login");

        LoginResponseDto result = loginService.guestLogin();
        return CommonResponse.onSuccess(result);
    }
}
