package com.curator.oeuvre.controller;

import com.curator.oeuvre.config.CommonResponse;
import com.curator.oeuvre.domain.User;
import com.curator.oeuvre.dto.comment.reqeust.PostCommentRequestDto;
import com.curator.oeuvre.dto.comment.response.GetCommentResponseDto;
import com.curator.oeuvre.dto.comment.response.GetFloorCommentsResponseDto;
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
@Api(tags = "06. 방명록 📖")
@RequiredArgsConstructor
@Validated
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{floorNo}")
    @Operation(summary = "방명록 댓글 생성", description = "방명록 댓글 생성 API 입니다.")
    public CommonResponse<PostCommentResponseDto> postComment(@AuthenticationPrincipal User authUser,
                                                              @PathVariable Long floorNo,
                                                             @Valid @RequestBody PostCommentRequestDto postCommentRequestDto, BindingResult bindingResult) {
        log.info("post-comment");
        log.info("api = 방명록 댓글 생성, user = {}", authUser.getNo());

        if (bindingResult.hasErrors()) {
            ObjectError objectError = bindingResult.getAllErrors().stream().findFirst().get();
            return CommonResponse.onFailure("400", objectError.getDefaultMessage(), null);
        }
        PostCommentResponseDto result = commentService.postComment(authUser, floorNo, postCommentRequestDto);
        return CommonResponse.onSuccess(result);
    }

    @DeleteMapping("/{commentNo}")
    @Operation(summary = "방명록 댓글 삭제", description = "방명록 댓글 삭제 API 입니다.")
    public CommonResponse<String> deleteComment(@AuthenticationPrincipal User authUser,
                                                              @PathVariable Long commentNo) {
        log.info("delete-comment");
        log.info("api = 방명록 댓글 삭제, user = {}", authUser.getNo());

        commentService.deleteComment(authUser, commentNo);
        return CommonResponse.onSuccess("댓글 삭제 성공");
    }

    @GetMapping("/{floorNo}")
    @Operation(summary = "플로어 방명록 조회", description = "플로어 방명록 조회 API 입니다.\n" +
                         "댓글들을 최신순으로 size개씩 페이지네이션 해서 보여줍니다.\n" +
                         "page는 0부터 시작합니다. size는 10-50 가능합니다.")
    public CommonResponse<GetFloorCommentsResponseDto> getFloorComments(
            @AuthenticationPrincipal User authUser,
            @PathVariable Long floorNo,
            @Parameter(description = "페이지", example = "0") @RequestParam(required = true) @Min(value = 0) Integer page,
            @Parameter(description = "페이지 사이즈", example = "10") @RequestParam(required = true) @Min(value = 10) @Max(value = 50) Integer size) {
        log.info("get-floor-comments");
        log.info("api = 플로어 방명록 조회, user = {}", authUser.getNo());

        GetFloorCommentsResponseDto result = commentService.getFloorComments(authUser, floorNo, page, size);
        return CommonResponse.onSuccess(result);
    }

    @GetMapping("/{floorNo}/other-floors")
    @Operation(summary = "방명록 이동 플로어 전체 조회", description = "현재 플로어 방명록에서 이동할 수 있는 플로어 목록을 조회 하는 API 입니다.\n" +
                                                                "방명록이 허용된 플로어만 보여지며, 타인일 경우 공개 플로어만 보여집니다.")
    public CommonResponse<List<GetFloorToMoveResponseDto>> getFloorsToMove(@AuthenticationPrincipal User authUser, @PathVariable Long floorNo) {
        log.info("get-floors-to-move");
        log.info("api = 방명록 이동 플로어 전체 조회, user = {}", authUser.getNo());

        List<GetFloorToMoveResponseDto> result = commentService.getFloorsToMove(authUser, floorNo);
        return CommonResponse.onSuccess(result);
    }
}
