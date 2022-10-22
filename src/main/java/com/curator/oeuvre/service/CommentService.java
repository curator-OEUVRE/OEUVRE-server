package com.curator.oeuvre.service;

import com.curator.oeuvre.domain.User;
import com.curator.oeuvre.dto.comment.reqeust.PostCommentRequestDto;
import com.curator.oeuvre.dto.comment.response.PostCommentResponseDto;
import org.springframework.stereotype.Service;

@Service
public interface CommentService {

    PostCommentResponseDto postComment(User user, Long floorNo, PostCommentRequestDto postCommentRequestDto);

    void deleteComment(User user, Long commentNo);
}
