package com.curator.oeuvre.service;

import com.curator.oeuvre.domain.Comment;
import com.curator.oeuvre.domain.Floor;
import com.curator.oeuvre.domain.User;
import com.curator.oeuvre.dto.comment.reqeust.PostCommentRequestDto;
import com.curator.oeuvre.dto.comment.response.GetCommentResponseDto;
import com.curator.oeuvre.dto.comment.response.PostCommentResponseDto;
import com.curator.oeuvre.dto.hashtag.response.GetHashtagResponseDto;
import com.curator.oeuvre.exception.ForbiddenException;
import com.curator.oeuvre.exception.NotFoundException;
import com.curator.oeuvre.repository.CommentRepository;
import com.curator.oeuvre.repository.FloorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.curator.oeuvre.constant.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final FloorRepository floorRepository;
    private final CommentRepository commentRepository;

    @Override
    public PostCommentResponseDto postComment(User user, Long floorNo, PostCommentRequestDto postCommentRequestDto) {

        Floor floor = floorRepository.findByNoAndStatus(floorNo, 1).orElseThrow(() ->
                new NotFoundException(FLOOR_NOT_FOUND));

        Comment comment = Comment.builder()
                .floor(floor)
                .user(user)
                .comment(postCommentRequestDto.getComment())
                .build();
        commentRepository.save(comment);

        return new PostCommentResponseDto(comment);
    }

    @Override
    @Transactional
    public void deleteComment(User user, Long commentNo) {

        Comment comment = commentRepository.findByNoAndStatus(commentNo, 1).orElseThrow(() ->
                new NotFoundException(COMMENT_NOT_FOUND));

        // 자신의 댓글 또는 자신 플로어의 댓글이 아닐경우 삭제 권한 없음
        if (!Objects.equals(comment.getUser().getNo(), user.getNo())
                && !Objects.equals(comment.getFloor().getUser().getNo(), user.getNo()))
            throw new ForbiddenException(FORBIDDEN_COMMENT);

        commentRepository.deleteByNo(commentNo);
    }

    @Override
    public List<GetCommentResponseDto> getFloorComments(User user, Long floorNo, Integer page, Integer size) {

        floorRepository.findByNoAndStatus(floorNo, 1).orElseThrow(() ->
                new NotFoundException(FLOOR_NOT_FOUND));

        Pageable pageRequest = PageRequest.of(page, size);
        Page<Comment> comments = commentRepository.findAllByFloorNoAndStatusOrderByCreatedAtDesc(floorNo, 1, pageRequest);

        List<GetCommentResponseDto> result = new ArrayList<>();
        comments.forEach( comment -> {
            result.add(new GetCommentResponseDto(comment, Objects.equals(user.getNo(), comment.getUser().getNo())));
        });
        return result;
    }
}
