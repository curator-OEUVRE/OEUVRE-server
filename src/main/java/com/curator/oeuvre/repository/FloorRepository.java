package com.curator.oeuvre.repository;

import com.curator.oeuvre.domain.Floor;
import com.curator.oeuvre.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FloorRepository extends JpaRepository <Floor, Long> {

    Integer countFloorByUser(User user);

    Optional<Floor> findByNoAndStatus(Long floorNo, Integer status);

    Page<Floor> findAllByUserNoAndStatusAndIsGroupExhibitionOrderByQueueDesc(Long userNo, Integer status, Boolean isGroupExhibition, Pageable pageable);

    Page<Floor> findAllByUserNoAndStatusAndIsPublicAndIsGroupExhibitionOrderByQueueDesc(Long userNo, Integer status, Boolean isPublic, Boolean isGroupExhibition, Pageable pageable);

    List<Floor> findAllByUserNoAndStatusAndIsCommentAvailableAndIsGroupExhibitionOrderByQueueDesc(Long userNo, Integer status, Boolean isCommentAvailable, Boolean isGroupExhibition);

    List<Floor> findAllByUserNoAndStatusAndIsCommentAvailableAndIsPublicAndIsGroupExhibitionOrderByQueueDesc(Long userNo, Integer status, Boolean isCommentAvailable, Boolean isPublic, Boolean isGroupExhibition);

}
