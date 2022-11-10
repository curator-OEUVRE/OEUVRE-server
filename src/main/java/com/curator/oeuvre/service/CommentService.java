package com.curator.oeuvre.service;

import com.curator.oeuvre.domain.User;
import com.curator.oeuvre.dto.comment.reqeust.PostCommentRequestDto;
import com.curator.oeuvre.dto.comment.response.GetCommentResponseDto;
import com.curator.oeuvre.dto.comment.response.GetFloorToMoveResponseDto;
import com.curator.oeuvre.dto.comment.response.PostCommentResponseDto;
import com.curator.oeuvre.dto.common.response.PageResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CommentService {

    PostCommentResponseDto postComment(User user, Long floorNo, PostCommentRequestDto postCommentRequestDto);

    void deleteComment(User user, Long commentNo);

    PageResponseDto<List<GetCommentResponseDto>> getFloorComments(User user, Long floorNo, Integer page, Integer size);

    List<GetFloorToMoveResponseDto> getFloorsToMove(User user, Long floorNo);
}
