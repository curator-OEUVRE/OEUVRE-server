package com.curator.oeuvre.controller;

import com.curator.oeuvre.config.CommonResponse;
import com.curator.oeuvre.domain.User;
import com.curator.oeuvre.dto.user.request.SignUpRequestDto;
import com.curator.oeuvre.dto.user.response.CheckIdResponseDto;
import com.curator.oeuvre.dto.user.response.GetMyProfileResponseDto;
import com.curator.oeuvre.dto.user.response.SignUpResponseDto;
import com.curator.oeuvre.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Slf4j
@RequestMapping("/users")
@Api(tags = "02. 사용자 👤")
@RequiredArgsConstructor
@Validated
public class UserController {

    private final UserService userService;

    @PostMapping
    @Operation(summary = "회원가입", description = "회원가입 API 입니다.\n로그인 실패시 반환된 소셜 이메일 + 필요한 정보들을 입력받아 유저를 생성합니다.")
    public CommonResponse<SignUpResponseDto> signUp(@Valid @RequestBody SignUpRequestDto signUpRequestDto, BindingResult bindingResult) {
        log.info("sign-up");

        if (bindingResult.hasErrors()) {
            ObjectError objectError = bindingResult.getAllErrors().stream().findFirst().get();
            return CommonResponse.onFailure("400", objectError.getDefaultMessage(), null);
        }
        SignUpResponseDto result = userService.signUp(signUpRequestDto);
        return CommonResponse.onSuccess(result);
    }

    @GetMapping(value = "/check-id")
    @Operation(summary = "ID 중복 검사", description = "ID 사용가능 여부를 검사하는 API 입니다.\n 길이와 중복 여부를 검사하며, 회원가입 전 ID 입력 시에 사용합니다.")
    public CommonResponse<CheckIdResponseDto> checkId(@Parameter(description = "id", example = "one_zzini_")
                                  @RequestParam(required = true)
                                  @Length(min = 4, max = 15) String id) {

        CheckIdResponseDto result = userService.checkId(id);
        return CommonResponse.onSuccess(result);
    }

    @GetMapping(value = "/my-profile")
    @Operation(summary = "내 프로필 정보 조회", description = "내 프로필 정보를 조회하는 API 입니다.")
    public CommonResponse<GetMyProfileResponseDto> geyMyProfile(@AuthenticationPrincipal User authUser) {

        GetMyProfileResponseDto result = userService.getMyProfile(authUser);
        return CommonResponse.onSuccess(result);
    }
}

