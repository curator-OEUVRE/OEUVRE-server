package com.curator.oeuvre.repository;

import com.curator.oeuvre.domain.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository <Notification, Long> {

    Page<Notification> findAllByUserNoAndStatusOrderByCreatedAtDesc(Long userNo, Integer status, Pageable pageable);

    void deleteAllByTypeAndUserNoAndSendUserNoAndStatus(String type, Long userNo, Long sendUserNo, Integer status);

    void deleteAllByTypeAndUserNoAndSendUserNoAndLikesNoAndStatus(String type, Long userNo, Long sendUserNo, Long LikeNo, Integer status);

    void deleteAllByTypeAndCommentNoAndStatus(String type, Long commentNo, Integer status);

}
