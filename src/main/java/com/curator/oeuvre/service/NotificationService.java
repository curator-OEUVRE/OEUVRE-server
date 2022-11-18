package com.curator.oeuvre.service;

import com.curator.oeuvre.domain.Comment;
import com.curator.oeuvre.domain.Likes;
import com.curator.oeuvre.domain.User;
import com.curator.oeuvre.dto.common.response.PageResponseDto;
import com.curator.oeuvre.dto.notification.response.GetNotificationResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface NotificationService {

    PageResponseDto<List<GetNotificationResponseDto>> getNotifications(User user, Integer page, Integer size);

    void postNotification(User user, String type, User sendUser, Comment comment, Likes likes, Boolean isRead);
}
