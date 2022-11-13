package com.curator.oeuvre.service;

import com.curator.oeuvre.domain.*;
import com.curator.oeuvre.dto.common.response.PageResponseDto;
import com.curator.oeuvre.dto.notification.response.GetNotificationResponseDto;
import com.curator.oeuvre.repository.BlockRepository;
import com.curator.oeuvre.repository.FollowingRepository;
import com.curator.oeuvre.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final BlockRepository blockRepository;
    private final FollowingRepository followingRepository;

    @Override
    public PageResponseDto<List<GetNotificationResponseDto>> getNotifications(User user, Integer page, Integer size) {

        Pageable pageRequest = PageRequest.of(page, size);

        Page<Notification> notifications = notificationRepository.
                findAllByUserNoAndStatusOrderByCreatedAtDesc(user.getNo(), 1, pageRequest);
        List<Long> blockedUserNos = blockRepository.findAllByBlockUserNoAndStatus(user.getNo(), 1).stream().map(no -> no.getBlockedUser().getNo()).collect(Collectors.toList());

        List<GetNotificationResponseDto> result = new ArrayList<>();
        notifications.forEach( notification -> {
            if(!blockedUserNos.contains(notification.getSendUser().getNo()) && notification.getSendUser().getStatus() == 1) {
                if (Objects.equals(notification.getType(), "FOLLOWING")) {
                    boolean isFollowing = followingRepository.existsByFollowUserNoAndFollowedUserNoAndStatus(user.getNo(), notification.getSendUser().getNo(), 1);
                    result.add(new GetNotificationResponseDto(notification, isFollowing, null, null, null, null));
                }
                else if (Objects.equals(notification.getType(), "COMMENT") && notification.getComment().getFloor().getStatus() == 1) {
                    result.add(new GetNotificationResponseDto(notification, null, notification.getComment().getFloor().getNo(),
                            null, notification.getComment().getNo(), notification.getComment().getComment()));
                }
                else if (Objects.equals(notification.getType(), "LIKES") && notification.getLikes().getPicture().getStatus() == 1) {
                    result.add(new GetNotificationResponseDto(notification, null, null,
                            notification.getLikes().getPicture().getNo(), null, null));
                }
            }
        });
        return new PageResponseDto<>(notifications.isLast(), result);
    }

    @Override
    public void postNotification(User user, String type, User sendUser, Comment comment, Likes likes, Boolean isRead) {

        Notification notification = Notification.builder()
                .user(user)
                .type(type)
                .sendUser(sendUser)
                .comment(comment)
                .likes(likes)
                .isRead(isRead)
                .build();
        notificationRepository.save(notification);
    }
}