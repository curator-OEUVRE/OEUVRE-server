package com.curator.oeuvre.controller;

import com.curator.oeuvre.config.CommonResponse;
import com.curator.oeuvre.domain.User;
import com.curator.oeuvre.dto.comment.reqeust.PostCommentRequestDto;
import com.curator.oeuvre.dto.comment.response.GetCommentResponseDto;
import com.curator.oeuvre.dto.comment.response.GetFloorToMoveResponseDto;
import com.curator.oeuvre.dto.comment.response.PostCommentResponseDto;
import com.curator.oeuvre.dto.common.response.PageResponseDto;
import com.curator.oeuvre.service.CommentService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@RequestMapping("/comments")
@Api(tags = "06. λ°©λͺλ‘ π")
@RequiredArgsConstructor
@Validated
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{floorNo}")
    @Operation(summary = "λ°©λͺλ‘ λκΈ μμ±", description = "λ°©λͺλ‘ λκΈ μμ± API μλλ€.")
    public CommonResponse<PostCommentResponseDto> postComment(@AuthenticationPrincipal User authUser,
                                                              @PathVariable Long floorNo,
                                                             @Valid @RequestBody PostCommentRequestDto postCommentRequestDto, BindingResult bindingResult) {
        log.info("post-comment");
        log.info("api = λ°©λͺλ‘ λκΈ μμ±, user = {}", authUser.getNo());

        if (bindingResult.hasErrors()) {
            ObjectError objectError = bindingResult.getAllErrors().stream().findFirst().get();
            return CommonResponse.onFailure("400", objectError.getDefaultMessage(), null);
        }
        PostCommentResponseDto result = commentService.postComment(authUser, floorNo, postCommentRequestDto);
        return CommonResponse.onSuccess(result);
    }

    @DeleteMapping("/{commentNo}")
    @Operation(summary = "λ°©λͺλ‘ λκΈ μ­μ ", description = "λ°©λͺλ‘ λκΈ μ­μ  API μλλ€.")
    public CommonResponse<String> deleteComment(@AuthenticationPrincipal User authUser,
                                                              @PathVariable Long commentNo) {
        log.info("delete-comment");
        log.info("api = λ°©λͺλ‘ λκΈ μ­μ , user = {}", authUser.getNo());

        commentService.deleteComment(authUser, commentNo);
        return CommonResponse.onSuccess("λκΈ μ­μ  μ±κ³΅");
    }

    @GetMapping("/{floorNo}")
    @Operation(summary = "νλ‘μ΄ λ°©λͺλ‘ μ‘°ν", description = "νλ‘μ΄ λ°©λͺλ‘ μ‘°ν API μλλ€.\n" +
                         "λκΈλ€μ μ΅μ μμΌλ‘ sizeκ°μ© νμ΄μ§λ€μ΄μ ν΄μ λ³΄μ¬μ€λλ€.\n" +
                         "pageλ 0λΆν° μμν©λλ€. sizeλ 10-50 κ°λ₯ν©λλ€.")
    public CommonResponse<PageResponseDto<List<GetCommentResponseDto>>> getFloorComments(
            @AuthenticationPrincipal User authUser,
            @PathVariable Long floorNo,
            @Parameter(description = "νμ΄μ§", example = "0") @RequestParam(required = true) @Min(value = 0) Integer page,
            @Parameter(description = "νμ΄μ§ μ¬μ΄μ¦", example = "10") @RequestParam(required = true) @Min(value = 10) @Max(value = 50) Integer size) {
        log.info("get-floor-comments");
        log.info("api = νλ‘μ΄ λ°©λͺλ‘ μ‘°ν, user = {}", authUser.getNo());

        PageResponseDto<List<GetCommentResponseDto>> result = commentService.getFloorComments(authUser, floorNo, page, size);
        return CommonResponse.onSuccess(result);
    }

    @GetMapping("/{floorNo}/other-floors")
    @Operation(summary = "λ°©λͺλ‘ μ΄λ νλ‘μ΄ μ μ²΄ μ‘°ν", description = "νμ¬ νλ‘μ΄ λ°©λͺλ‘μμ μ΄λν  μ μλ νλ‘μ΄ λͺ©λ‘μ μ‘°ν νλ API μλλ€.\n" +
                                                                "λ°©λͺλ‘μ΄ νμ©λ νλ‘μ΄λ§ λ³΄μ¬μ§λ©°, νμΈμΌ κ²½μ° κ³΅κ° νλ‘μ΄λ§ λ³΄μ¬μ§λλ€.")
    public CommonResponse<List<GetFloorToMoveResponseDto>> getFloorsToMove(@AuthenticationPrincipal User authUser, @PathVariable Long floorNo) {
        log.info("get-floors-to-move");
        log.info("api = λ°©λͺλ‘ μ΄λ νλ‘μ΄ μ μ²΄ μ‘°ν, user = {}", authUser.getNo());

        List<GetFloorToMoveResponseDto> result = commentService.getFloorsToMove(authUser, floorNo);
        return CommonResponse.onSuccess(result);
    }
}
