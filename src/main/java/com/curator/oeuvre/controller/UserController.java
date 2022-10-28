package com.curator.oeuvre.controller;

import com.curator.oeuvre.config.CommonResponse;
import com.curator.oeuvre.domain.User;
import com.curator.oeuvre.dto.user.request.PatchMyProfileRequestDto;
import com.curator.oeuvre.dto.user.request.SignUpRequestDto;
import com.curator.oeuvre.dto.user.response.*;
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
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

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

        log.info("get-my-profile");
        log.info("api = 내 프로필 조회, user = {}", authUser.getNo());

        GetMyProfileResponseDto result = userService.getMyProfile(authUser);
        return CommonResponse.onSuccess(result);
    }

    @PatchMapping(value = "/my-profile")
    @Operation(summary = "내 프로필 편집", description = "내 프로필 편집 내용을 업데이트하는 API 입니다.")
    public CommonResponse<String> patchMyProfile(@AuthenticationPrincipal User authUser,
                                                 @Valid @RequestBody PatchMyProfileRequestDto patchMyProfileRequestDto, BindingResult bindingResult) {
        log.info("patch-my-profile");
        log.info("api = 내 프로필 편집, user = {}", authUser.getNo());

        if (bindingResult.hasErrors()) {
            ObjectError objectError = bindingResult.getAllErrors().stream().findFirst().get();
            return CommonResponse.onFailure("400", objectError.getDefaultMessage(), null);
        }

        userService.patchMyProfile(authUser, patchMyProfileRequestDto);
        return CommonResponse.onSuccess("프로필 편집 성공");
    }


    @GetMapping(value = "/floors")
    @Operation(summary = "내 플로어 전체 조회", description = "내 플로어를 전체 조회하는 API 입니다.")
    public CommonResponse<List<GetMyFloorResponseDto>> geyMyFloors(@AuthenticationPrincipal User authUser,
                                                                   @Parameter(description = "페이지", example = "0") @RequestParam(required = true) @Min(value = 0) Integer page,
                                                                   @Parameter(description = "페이지 사이즈", example = "10") @RequestParam(required = true) @Min(value = 10) @Max(value = 50) Integer size) {

        log.info("get-my-floor");
        log.info("api = 내 플로어 전체 조회, user = {}", authUser.getNo());

        List<GetMyFloorResponseDto> result = userService.getMyFloors(authUser, page, size);
        return CommonResponse.onSuccess(result);
    }

    @GetMapping(value = "/collection")
    @Operation(summary = "내 컬렉션 전체 조회", description = "내가 스크랩한 사진을 전체 조회하는 API 입니다.")
    public CommonResponse<List<GetMyCollectionResponseDto>> geyMyCollection(@AuthenticationPrincipal User authUser,
                                                                            @Parameter(description = "페이지", example = "0") @RequestParam(required = true) @Min(value = 0) Integer page,
                                                                            @Parameter(description = "페이지 사이즈", example = "10") @RequestParam(required = true) @Min(value = 10) @Max(value = 50) Integer size) {

        log.info("get-my-collection");
        log.info("api = 내 컬렉션 조회, user = {}", authUser.getNo());

        List<GetMyCollectionResponseDto> result = userService.getMyCollection(authUser, page, size);
        return CommonResponse.onSuccess(result);
    }

    @GetMapping(value = "/{userNo}/profile")
    @Operation(summary = "타 유저 프로필 정보 조회", description = "타 유저 프로필 정보를 조회하는 API 입니다.")
    public CommonResponse<GetUserProfileResponseDto> geyUserProfile(@AuthenticationPrincipal User authUser,  @PathVariable Long userNo) {

        log.info("get-user-profile");
        log.info("api = 타 유저 프로필 조회, user = {}", authUser.getNo());

        GetUserProfileResponseDto result = userService.getUserProfile(authUser, userNo);
        return CommonResponse.onSuccess(result);
    }

}

