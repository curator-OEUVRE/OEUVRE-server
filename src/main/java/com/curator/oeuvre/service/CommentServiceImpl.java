package com.curator.oeuvre.service;

import com.curator.oeuvre.domain.Comment;
import com.curator.oeuvre.domain.Floor;
import com.curator.oeuvre.domain.Notification;
import com.curator.oeuvre.domain.User;
import com.curator.oeuvre.dto.comment.reqeust.PostCommentRequestDto;
import com.curator.oeuvre.dto.comment.response.GetCommentResponseDto;
import com.curator.oeuvre.dto.comment.response.GetFloorCommentsResponseDto;
import com.curator.oeuvre.dto.comment.response.GetFloorToMoveResponseDto;
import com.curator.oeuvre.dto.comment.response.PostCommentResponseDto;
import com.curator.oeuvre.dto.common.response.PageResponseDto;
import com.curator.oeuvre.exception.ForbiddenException;
import com.curator.oeuvre.exception.NotFoundException;
import com.curator.oeuvre.repository.CommentRepository;
import com.curator.oeuvre.repository.FloorRepository;
import com.curator.oeuvre.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import static com.curator.oeuvre.constant.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final FloorRepository floorRepository;
    private final CommentRepository commentRepository;
    private final NotificationService notificationService;
    private final NotificationRepository notificationRepository;
    private final ExpoNotificationService expoNotificationService;

    @Override
    @Transactional
    public PostCommentResponseDto postComment(User user, Long floorNo, PostCommentRequestDto postCommentRequestDto) {

        Floor floor = floorRepository.findByNoAndStatus(floorNo, 1).orElseThrow(() ->
                new NotFoundException(FLOOR_NOT_FOUND));

        if (!floor.getIsCommentAvailable()) throw new ForbiddenException(COMMENT_NOT_AVAILABLE);

        Comment comment = Comment.builder()
                .floor(floor)
                .user(user)
                .comment(postCommentRequestDto.getComment())
                .build();
        commentRepository.save(comment);

        if (!Objects.equals(user.getNo(), comment.getFloor().getUser().getNo())) {

            notificationService.postNotification(comment.getFloor().getUser(), "COMMENT", user, comment, null, false);

            if (comment.getFloor().getUser().getIsCommentAlarmOn())
            {
                HashMap<String, Object> data = new HashMap<>();
                data.put("sendUserNo", user.getNo());
                data.put("floorNo", comment.getFloor().getNo());
                data.put("commentNo", comment.getNo());
                String message = user.getId()+ "님이 회원님의 플로어에 방명록을 남겼습니다: " + comment.getComment();
                expoNotificationService.sendMessage(comment.getFloor().getUser(), "방명록 알림", message, data);
                expoNotificationService.postFcmLog(comment.getFloor().getUser(), "comment", data);
            }
        }

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

        notificationRepository.deleteAllByTypeAndCommentNoAndStatus("COMMENT", comment.getNo(), 1);
        commentRepository.deleteByNo(commentNo);
    }

    @Override
    @Transactional
    public GetFloorCommentsResponseDto getFloorComments(User user, Long floorNo, Integer page, Integer size) {

        Floor floor = floorRepository.findByNoAndStatus(floorNo, 1).orElseThrow(() ->
                new NotFoundException(FLOOR_NOT_FOUND));

        if (!floor.getIsCommentAvailable()) throw new ForbiddenException(COMMENT_NOT_AVAILABLE);

        Pageable pageRequest = PageRequest.of(page, size);
        Page<Comment> comments = commentRepository.findAllByFloorNoAndStatusOrderByCreatedAtAsc(floorNo, 1, pageRequest);

        List<GetCommentResponseDto> result = new ArrayList<>();
        comments.forEach( comment -> {
            result.add(new GetCommentResponseDto(comment, Objects.equals(user.getNo(), comment.getUser().getNo())));
        });

        if (Objects.equals(user.getNo(), floor.getUser().getNo())) {
            List<Notification> notifications = notificationRepository.findAllByUserNoAndTypeAndComment_FloorNoAndIsReadAndStatus(
                    user.getNo(), "COMMENT", floor.getNo(), false, 1);
            notifications.forEach( notification -> {
                notification.setIsRead(true);
            });
            notificationRepository.saveAll(notifications);
        }

        return new GetFloorCommentsResponseDto(floor.getName(), comments.isLast(), result);
    }

    @Override
    public List<GetFloorToMoveResponseDto> getFloorsToMove(User user, Long floorNo) {

        Floor floor = floorRepository.findByNoAndStatus(floorNo, 1).orElseThrow(() ->
                new NotFoundException(FLOOR_NOT_FOUND));

        List<Floor> otherFloors;
        if (!Objects.equals(floor.getUser().getNo(), user.getNo())) {
            otherFloors = floorRepository.findAllByUserNoAndStatusAndIsCommentAvailableAndIsPublicAndIsGroupExhibitionOrderByQueueDesc(
                    floor.getUser().getNo(), 1, true, true, false);
        } else {
            otherFloors = floorRepository.findAllByUserNoAndStatusAndIsCommentAvailableAndIsGroupExhibitionOrderByQueueDesc(
                    floor.getUser().getNo(), 1, true,false);
        }

        List<GetFloorToMoveResponseDto> result = new ArrayList<>();
        otherFloors.forEach(otherFloor -> { result.add(new GetFloorToMoveResponseDto(otherFloor));} );

        return result;
    }
}
