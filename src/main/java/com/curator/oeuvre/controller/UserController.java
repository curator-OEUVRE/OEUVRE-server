package com.curator.oeuvre.controller;

import com.curator.oeuvre.config.CommonResponse;
import com.curator.oeuvre.domain.User;
import com.curator.oeuvre.dto.common.response.PageResponseDto;
import com.curator.oeuvre.dto.hashtag.response.GetHashtagSearchResponseDto;
import com.curator.oeuvre.dto.user.response.GetUserFloorResponseDto;
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

    @GetMapping(value = "/{userNo}/floors")
    @Operation(summary = "유저 플로어 전체 조회", description = "해당 유저의 플로어를 전체 조회하는 API 입니다.")
    public CommonResponse<PageResponseDto<List<GetUserFloorResponseDto>>> getUserFloors(@AuthenticationPrincipal User authUser, @PathVariable Long userNo,
                                                                       @Parameter(description = "페이지", example = "0") @RequestParam(required = true) @Min(value = 0) Integer page,
                                                                       @Parameter(description = "페이지 사이즈", example = "10") @RequestParam(required = true) @Min(value = 10) @Max(value = 50) Integer size) {

        log.info("get-user-floor");
        log.info("api = 유저 플로어 전체 조회, user = {}", authUser.getNo());

        PageResponseDto<List<GetUserFloorResponseDto>> result = userService.getUserFloors(authUser, userNo, page, size);
        return CommonResponse.onSuccess(result);
    }

    @GetMapping(value = "/collection")
    @Operation(summary = "내 컬렉션 전체 조회", description = "내가 스크랩한 사진을 전체 조회하는 API 입니다.")
    public CommonResponse<PageResponseDto<List<GetMyCollectionResponseDto>>> geyMyCollection(@AuthenticationPrincipal User authUser,
                                                                                            @Parameter(description = "페이지", example = "0") @RequestParam(required = true) @Min(value = 0) Integer page,
                                                                                            @Parameter(description = "페이지 사이즈", example = "10") @RequestParam(required = true) @Min(value = 10) @Max(value = 50) Integer size) {

        log.info("get-my-collection");
        log.info("api = 내 컬렉션 전체 조회, user = {}", authUser.getNo());

        PageResponseDto<List<GetMyCollectionResponseDto>> result = userService.getMyCollection(authUser, page, size);
        return CommonResponse.onSuccess(result);
    }

    @GetMapping(value = "/{userNo}/profile")
    @Operation(summary = "타 유저 프로필 정보 조회", description = "타 유저 프로필 정보를 조회하는 API 입니다.")
    public CommonResponse<GetUserProfileResponseDto> getUserProfile(@AuthenticationPrincipal User authUser, @PathVariable Long userNo) {

        log.info("get-user-profile");
        log.info("api = 타 유저 프로필 조회, user = {}", authUser.getNo());

        GetUserProfileResponseDto result = userService.getUserProfile(authUser, userNo);
        return CommonResponse.onSuccess(result);
    }

    @PostMapping(value = "/{userNo}/follow")
    @Operation(summary = "유저 팔로우", description = "해당 유저를 팔로우하는 API 입니다.")
    public CommonResponse<String> postFollow(@AuthenticationPrincipal User authUser, @PathVariable Long userNo) {

        log.info("post-follow");
        log.info("api = 유저 팔로우, user = {}", authUser.getNo());

        userService.postFollow(authUser, userNo);
        return CommonResponse.onSuccess("유저 팔로우 성공");
    }

    @DeleteMapping(value = "/{userNo}/follow")
    @Operation(summary = "유저 팔로우 취소", description = "해당 유저 팔로우를 취소하는 API 입니다.")
    public CommonResponse<String> deleteFollow(@AuthenticationPrincipal User authUser, @PathVariable Long userNo) {

        log.info("delete-follow");
        log.info("api = 유저 팔로우 취소, user = {}", authUser.getNo());

        userService.deleteFollow(authUser, userNo);
        return CommonResponse.onSuccess("유저 팔로우 취소 성공");
    }

    @GetMapping(value = "/{userNo}/following")
    @Operation(summary = "유저 팔로잉 전체 조회", description = "해당 유저가 팔로우하는 유저를 전체 조회하는 API 입니다.")
    public CommonResponse<List<GetUserFollowingResponseDto>> getUserFollowings(@AuthenticationPrincipal User authUser, @PathVariable Long userNo) {

        log.info("get-user-followings");
        log.info("api = 유저 팔로잉 전체 조회, user = {}", authUser.getNo());

        List<GetUserFollowingResponseDto> result = userService.getUserFollowings(authUser, userNo);
        return CommonResponse.onSuccess(result);
    }

    @GetMapping(value = "/{userNo}/follower")
    @Operation(summary = "유저 팔로워 전체 조회", description = "해당 유저를 팔로우하는 유저를 전체 조회하는 API 입니다.")
    public CommonResponse<List<GetUserFollowerResponseDto>> getUserFollowers(@AuthenticationPrincipal User authUser, @PathVariable Long userNo) {

        log.info("get-user-followers");
        log.info("api = 유저 팔로워 전체 조회, user = {}", authUser.getNo());

        List<GetUserFollowerResponseDto> result = userService.getUserFollowers(authUser, userNo);
        return CommonResponse.onSuccess(result);
    }

    @GetMapping
    @Operation(summary = "유저(계정) 검색", description = "탐색탭 계정 검색 API 입니다.\n" +
            "keyword를 포함하는 이름, keyword로 시작하는 id을 가진 유저들을 size개씩 페이지네이션 해서 보여줍니다.\n" +
            "page는 0부터 시작합니다. size는 10-50 가능합니다.")
    public CommonResponse<PageResponseDto<List<GetUserSearchResponseDto>>> searchUsers(
            @Parameter(description = "검색어", example = "one") @RequestParam(required = true) String keyword,
            @Parameter(description = "페이지", example = "0") @RequestParam(required = true) @Min(value = 0) Integer page,
            @Parameter(description = "페이지 사이즈", example = "10") @RequestParam(required = true) @Min(value = 10) @Max(value = 50) Integer size) {
        log.info("search-users");
        log.info("api = 유저 검색");

        PageResponseDto<List<GetUserSearchResponseDto>> result = userService.searchUsers(keyword, page, size);
        return CommonResponse.onSuccess(result);
    }
}

