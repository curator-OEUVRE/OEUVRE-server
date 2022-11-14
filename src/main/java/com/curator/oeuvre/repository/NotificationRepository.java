package com.curator.oeuvre.repository;

import com.curator.oeuvre.domain.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.sql.Timestamp;
import java.util.List;

public interface NotificationRepository extends JpaRepository <Notification, Long> {

    Page<Notification> findAllByUserNoAndStatusAndCreatedAtAfterOrderByCreatedAtDesc(Long userNo, Integer status, Timestamp createdAt, Pageable pageable);

    void deleteAllByTypeAndUserNoAndSendUserNoAndStatus(String type, Long userNo, Long sendUserNo, Integer status);

    void deleteAllByTypeAndUserNoAndSendUserNoAndLikesNoAndStatus(String type, Long userNo, Long sendUserNo, Long LikeNo, Integer status);

    void deleteAllByTypeAndCommentNoAndStatus(String type, Long commentNo, Integer status);

    List<Notification> findAllByUserNoAndTypeAndComment_FloorNoAndIsReadAndStatus(Long userNo, String type, Long floorNo, Boolean isRead, Integer status);

    List<Notification> findAllByUserNoAndTypeAndLikes_PictureNoAndIsReadAndStatus(Long userNo, String type, Long pictureNo, Boolean isRead, Integer status);

    Boolean existsByUserNoAndTypeAndComment_FloorNoAndIsReadAndStatus(Long userNo, String type, Long floorNo, Boolean isRead, Integer status);
}
