package com.curator.oeuvre.repository;

import com.curator.oeuvre.domain.Floor;
import com.curator.oeuvre.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FloorRepository extends JpaRepository <Floor, Long> {

    Integer countFloorByUser(User user);

    Optional<Floor> findByNoAndStatus(Long floorNo, Integer status);

    Page<Floor> findAllByUserNoAndStatusAndIsGroupExhibitionOrderByQueueDesc(Long userNo, Integer status, Boolean isGroupExhibition, Pageable pageable);

    Page<Floor> findAllByUserNoAndStatusAndIsPublicAndIsGroupExhibitionOrderByQueueDesc(Long userNo, Integer status, Boolean isPublic, Boolean isGroupExhibition, Pageable pageable);

    List<Floor> findAllByUserNoAndStatusAndIsCommentAvailableAndIsGroupExhibitionOrderByQueueDesc(Long userNo, Integer status, Boolean isCommentAvailable, Boolean isGroupExhibition);

    List<Floor> findAllByUserNoAndStatusAndIsCommentAvailableAndIsPublicAndIsGroupExhibitionOrderByQueueDesc(Long userNo, Integer status, Boolean isCommentAvailable, Boolean isPublic, Boolean isGroupExhibition);

    @Query(value = "(SELECT distinct floor.no as floorNo, floor.name as floorName, floor.queue, user.exhibition_name, " +
            "(SELECT picture.image_url FROM oeuvre.picture WHERE picture.floor_no = floor.no and picture.status = 1 ORDER BY picture.queue desc LIMIT 1) as thumbnailUrl, " +
            "(SELECT picture.height FROM oeuvre.picture WHERE picture.floor_no = floor.no and picture.status = 1 ORDER BY picture.queue desc LIMIT 1) as height, " +
            "(SELECT picture.width FROM oeuvre.picture WHERE picture.floor_no = floor.no and picture.status = 1 ORDER BY picture.queue desc LIMIT 1) as width, " +
            "user.no as userNo, user.id, user.profile_image_url, " +
            "ifnull(floor_read.is_new, false) as isNew, ifnull(floor_read.is_updated, false) as isUpdated, " +
            "ifnull(floor_read.update_count, 0) as updateCount, false as isMine, floor.updated_at as updatedAt " +
            "FROM oeuvre.floor JOIN oeuvre.user on floor.user_no = user.no " +
            "LEFT JOIN oeuvre.floor_read on floor.no = floor_read.floor_no and :userNo = floor_read.user_no " +
            "LEFT JOIN oeuvre.block on user.no = block.blocked_user_no " +
            "JOIN oeuvre.following on user.no = following.followed_user_no and :userNo = following.follow_user_no " +
            "WHERE user.no not in (SELECT blocked_user_no FROM block WHERE block_user_no = :userNo) " +
            "and floor.status = 1 and floor.is_public is true) " +
            "UNION " +
            "(SELECT distinct floor.no as floorNo, floor.name as floorName, floor.queue, user.exhibition_name, " +
            "(SELECT picture.image_url FROM oeuvre.picture WHERE picture.floor_no = floor.no and picture.status = 1 ORDER BY picture.queue desc LIMIT 1) as thumbnailUrl, " +
            "(SELECT picture.height FROM oeuvre.picture WHERE picture.floor_no = floor.no and picture.status = 1 ORDER BY picture.queue desc LIMIT 1) as height, " +
            "(SELECT picture.width FROM oeuvre.picture WHERE picture.floor_no = floor.no and picture.status = 1 ORDER BY picture.queue desc LIMIT 1) as width, " +
            "user.no as userNo, user.id, user.profile_image_url, " +
            "false as isNew, false as isUpdated, 0 as updateCount, true as isMine, floor.updated_at as updatedAt " +
            "FROM oeuvre.floor JOIN oeuvre.user on floor.user_no = user.no " +
            "WHERE user.no = :userNo and floor.status = 1) " +
            "ORDER BY isNew desc, isUpdated desc, updateCount desc, updatedAt desc ",
            countQuery = "SELECT count(*) FROM ((SELECT distinct floor.no " +
                    "FROM oeuvre.floor JOIN oeuvre.user on floor.user_no = user.no " +
                    "LEFT JOIN oeuvre.floor_read on floor.no = floor_read.floor_no and :userNo = floor_read.user_no " +
                    "LEFT JOIN oeuvre.block on user.no = block.blocked_user_no " +
                    "JOIN oeuvre.following on user.no = following.followed_user_no and :userNo = following.follow_user_no " +
                    "WHERE user.no not in (SELECT blocked_user_no FROM block WHERE block_user_no = :userNo) " +
                    "and floor.status = 1 and floor.is_public is true) " +
                    "UNION " +
                    "(SELECT distinct floor.no as floorNo " +
                    "FROM oeuvre.floor JOIN oeuvre.user on floor.user_no = user.no " +
                    "WHERE user.no = :userNo and floor.status = 1)) as c ",
            nativeQuery = true)
    Page<GetHomeFloor> findHomeFloors(@Param("userNo") Long userNo, Pageable pageable);

    interface GetHomeFloor {
        Long getFloorNo();
        String getFloorName();
        Integer getQueue();
        String getExhibitionName();
        String getThumbnailUrl();
        Float getHeight();
        Float getWidth();
        Long getUserNo();
        String getId();
        String getProfileImageUrl();
        Integer getIsNew();
        Integer getIsUpdated();
        Integer getUpdateCount();
        Integer getIsMine();
        String getUpdatedAt();
    }

}

