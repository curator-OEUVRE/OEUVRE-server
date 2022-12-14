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
@Api(tags = "02. μ¬μ©μ π€")
@RequiredArgsConstructor
@Validated
public class UserController {

    private final UserService userService;

    @PostMapping
    @Operation(summary = "νμκ°μ", description = "νμκ°μ API μλλ€.\nλ‘κ·ΈμΈ μ€ν¨μ λ°νλ μμ μ΄λ©μΌ + νμν μ λ³΄λ€μ μλ ₯λ°μ μ μ λ₯Ό μμ±ν©λλ€.")
    public CommonResponse<SignUpResponseDto> signUp(@Valid @RequestBody SignUpRequestDto signUpRequestDto, BindingResult bindingResult) {
        log.info("sign-up");
        log.info("api = νμ κ°μ");

        if (bindingResult.hasErrors()) {
            ObjectError objectError = bindingResult.getAllErrors().stream().findFirst().get();
            return CommonResponse.onFailure("400", objectError.getDefaultMessage(), null);
        }
        SignUpResponseDto result = userService.signUp(signUpRequestDto);
        return CommonResponse.onSuccess(result);
    }

    @GetMapping(value = "/check-id")
    @Operation(summary = "ID μ€λ³΅ κ²μ¬", description = "ID μ¬μ©κ°λ₯ μ¬λΆλ₯Ό κ²μ¬νλ API μλλ€.\n κΈΈμ΄μ μ€λ³΅ μ¬λΆλ₯Ό κ²μ¬νλ©°, νμκ°μ μ  ID μλ ₯ μμ μ¬μ©ν©λλ€.")
    public CommonResponse<CheckIdResponseDto> checkId(@Parameter(description = "id", example = "one_zzini_")
                                  @RequestParam(required = true)
                                  @Length(min = 4, max = 15) String id) {

        CheckIdResponseDto result = userService.checkId(id);
        return CommonResponse.onSuccess(result);
    }

    @GetMapping(value = "/my-profile")
    @Operation(summary = "λ΄ νλ‘ν μ λ³΄ μ‘°ν", description = "λ΄ νλ‘ν μ λ³΄λ₯Ό μ‘°ννλ API μλλ€.")
    public CommonResponse<GetMyProfileResponseDto> geyMyProfile(@AuthenticationPrincipal User authUser) {

        log.info("get-my-profile");
        log.info("api = λ΄ νλ‘ν μ‘°ν, user = {}", authUser.getNo());

        GetMyProfileResponseDto result = userService.getMyProfile(authUser);
        return CommonResponse.onSuccess(result);
    }

    @PatchMapping(value = "/my-profile")
    @Operation(summary = "λ΄ νλ‘ν νΈμ§", description = "λ΄ νλ‘ν νΈμ§ λ΄μ©μ μλ°μ΄νΈνλ API μλλ€.")
    public CommonResponse<String> patchMyProfile(@AuthenticationPrincipal User authUser,
                                                 @Valid @RequestBody PatchMyProfileRequestDto patchMyProfileRequestDto, BindingResult bindingResult) {
        log.info("patch-my-profile");
        log.info("api = λ΄ νλ‘ν νΈμ§, user = {}", authUser.getNo());

        if (bindingResult.hasErrors()) {
            ObjectError objectError = bindingResult.getAllErrors().stream().findFirst().get();
            return CommonResponse.onFailure("400", objectError.getDefaultMessage(), null);
        }

        userService.patchMyProfile(authUser, patchMyProfileRequestDto);
        return CommonResponse.onSuccess("νλ‘ν νΈμ§ μ±κ³΅");
    }

    @GetMapping(value = "/{userNo}/floors")
    @Operation(summary = "μ μ  νλ‘μ΄ μ μ²΄ μ‘°ν", description = "ν΄λΉ μ μ μ νλ‘μ΄λ₯Ό μ μ²΄ μ‘°ννλ API μλλ€.")
    public CommonResponse<PageResponseDto<List<GetUserFloorResponseDto>>> getUserFloors(@AuthenticationPrincipal User authUser, @PathVariable Long userNo,
                                                                       @Parameter(description = "νμ΄μ§", example = "0") @RequestParam(required = true) @Min(value = 0) Integer page,
                                                                       @Parameter(description = "νμ΄μ§ μ¬μ΄μ¦", example = "10") @RequestParam(required = true) @Min(value = 10) @Max(value = 50) Integer size) {

        log.info("get-user-floor");
        log.info("api = μ μ  νλ‘μ΄ μ μ²΄ μ‘°ν, user = {}", authUser.getNo());

        PageResponseDto<List<GetUserFloorResponseDto>> result = userService.getUserFloors(authUser, userNo, page, size);
        return CommonResponse.onSuccess(result);
    }

    @GetMapping(value = "/collection")
    @Operation(summary = "λ΄ μ»¬λ μ μ μ²΄ μ‘°ν", description = "λ΄κ° μ€ν¬λ©ν μ¬μ§μ μ μ²΄ μ‘°ννλ API μλλ€.")
    public CommonResponse<PageResponseDto<List<GetMyCollectionResponseDto>>> geyMyCollection(@AuthenticationPrincipal User authUser,
                                                                                            @Parameter(description = "νμ΄μ§", example = "0") @RequestParam(required = true) @Min(value = 0) Integer page,
                                                                                            @Parameter(description = "νμ΄μ§ μ¬μ΄μ¦", example = "10") @RequestParam(required = true) @Min(value = 10) @Max(value = 50) Integer size) {

        log.info("get-my-collection");
        log.info("api = λ΄ μ»¬λ μ μ μ²΄ μ‘°ν, user = {}", authUser.getNo());

        PageResponseDto<List<GetMyCollectionResponseDto>> result = userService.getMyCollection(authUser, page, size);
        return CommonResponse.onSuccess(result);
    }

    @GetMapping(value = "/{userNo}/profile")
    @Operation(summary = "ν μ μ  νλ‘ν μ λ³΄ μ‘°ν", description = "ν μ μ  νλ‘ν μ λ³΄λ₯Ό μ‘°ννλ API μλλ€.")
    public CommonResponse<GetUserProfileResponseDto> getUserProfile(@AuthenticationPrincipal User authUser, @PathVariable Long userNo) {

        log.info("get-user-profile");
        log.info("api = ν μ μ  νλ‘ν μ‘°ν, user = {}", authUser.getNo());

        GetUserProfileResponseDto result = userService.getUserProfile(authUser, userNo);
        return CommonResponse.onSuccess(result);
    }

    @PostMapping(value = "/{userNo}/follow")
    @Operation(summary = "μ μ  νλ‘μ°", description = "ν΄λΉ μ μ λ₯Ό νλ‘μ°νλ API μλλ€.")
    public CommonResponse<String> postFollow(@AuthenticationPrincipal User authUser, @PathVariable Long userNo) {

        log.info("post-follow");
        log.info("api = μ μ  νλ‘μ°, user = {}", authUser.getNo());

        userService.postFollow(authUser, userNo);
        return CommonResponse.onSuccess("μ μ  νλ‘μ° μ±κ³΅");
    }

    @DeleteMapping(value = "/{userNo}/follow")
    @Operation(summary = "μ μ  νλ‘μ° μ·¨μ", description = "ν΄λΉ μ μ  νλ‘μ°λ₯Ό μ·¨μνλ API μλλ€.")
    public CommonResponse<String> deleteFollow(@AuthenticationPrincipal User authUser, @PathVariable Long userNo) {

        log.info("delete-follow");
        log.info("api = μ μ  νλ‘μ° μ·¨μ, user = {}", authUser.getNo());

        userService.deleteFollow(authUser, userNo);
        return CommonResponse.onSuccess("μ μ  νλ‘μ° μ·¨μ μ±κ³΅");
    }

    @GetMapping(value = "/{userNo}/following")
    @Operation(summary = "μ μ  νλ‘μ μ μ²΄ μ‘°ν", description = "ν΄λΉ μ μ κ° νλ‘μ°νλ μ μ λ₯Ό μ μ²΄ μ‘°ννλ API μλλ€.")
    public CommonResponse<List<GetUserFollowingResponseDto>> getUserFollowings(@AuthenticationPrincipal User authUser, @PathVariable Long userNo) {

        log.info("get-user-followings");
        log.info("api = μ μ  νλ‘μ μ μ²΄ μ‘°ν, user = {}", authUser.getNo());

        List<GetUserFollowingResponseDto> result = userService.getUserFollowings(authUser, userNo);
        return CommonResponse.onSuccess(result);
    }

    @GetMapping(value = "/{userNo}/follower")
    @Operation(summary = "μ μ  νλ‘μ μ μ²΄ μ‘°ν", description = "ν΄λΉ μ μ λ₯Ό νλ‘μ°νλ μ μ λ₯Ό μ μ²΄ μ‘°ννλ API μλλ€.")
    public CommonResponse<List<GetUserFollowerResponseDto>> getUserFollowers(@AuthenticationPrincipal User authUser, @PathVariable Long userNo) {

        log.info("get-user-followers");
        log.info("api = μ μ  νλ‘μ μ μ²΄ μ‘°ν, user = {}", authUser.getNo());

        List<GetUserFollowerResponseDto> result = userService.getUserFollowers(authUser, userNo);
        return CommonResponse.onSuccess(result);
    }

    @GetMapping
    @Operation(summary = "μ μ (κ³μ ) κ²μ", description = "νμν­ κ³μ  κ²μ API μλλ€.\n" +
            "keywordλ₯Ό ν¬ν¨νλ μ΄λ¦, keywordλ‘ μμνλ idμ κ°μ§ μ μ λ€μ sizeκ°μ© νμ΄μ§λ€μ΄μ ν΄μ λ³΄μ¬μ€λλ€.\n" +
            "pageλ 0λΆν° μμν©λλ€. sizeλ 10-50 κ°λ₯ν©λλ€.")
    public CommonResponse<PageResponseDto<List<GetUserSearchResponseDto>>> searchUsers(
            @AuthenticationPrincipal User authUser,
            @Parameter(description = "κ²μμ΄", example = "one") @RequestParam(required = true) String keyword,
            @Parameter(description = "νμ΄μ§", example = "0") @RequestParam(required = true) @Min(value = 0) Integer page,
            @Parameter(description = "νμ΄μ§ μ¬μ΄μ¦", example = "10") @RequestParam(required = true) @Min(value = 10) @Max(value = 50) Integer size) {
        log.info("search-users");
        log.info("api = μ μ  κ²μ");

        PageResponseDto<List<GetUserSearchResponseDto>> result = userService.searchUsers(authUser, keyword, page, size);
        return CommonResponse.onSuccess(result);
    }

    @DeleteMapping
    @Operation(summary = "νμ νν΄", description = "νμ νν΄ API μλλ€.\n νμ μνκ°μ λ³κ²½νκ³  μ μ μ κ΄λ ¨λ λ°μ΄ν°λ₯Ό μ­μ ν©λλ€.")
    public CommonResponse<String> deleteUser(@AuthenticationPrincipal User authUser) {
        log.info("delete-user");
        log.info("api = νμ νν΄, user = {}", authUser.getNo());

        userService.deleteUser(authUser);
        return CommonResponse.onSuccess("νμ νν΄ μ±κ³΅");
    }
}

