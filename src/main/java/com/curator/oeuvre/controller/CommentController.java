package com.curator.oeuvre.controller;

import com.curator.oeuvre.config.CommonResponse;
import com.curator.oeuvre.domain.User;
import com.curator.oeuvre.dto.comment.reqeust.PostCommentRequestDto;
import com.curator.oeuvre.dto.comment.response.PostCommentResponseDto;
import com.curator.oeuvre.dto.floor.response.PostFloorResponseDto;
import com.curator.oeuvre.service.CommentService;
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
}
