package com.curator.oeuvre.repository;

import com.curator.oeuvre.domain.Hashtag;
import com.curator.oeuvre.domain.PictureHashtag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PictureHashtagRepository extends JpaRepository <PictureHashtag, Long> {

    List<PictureHashtag> findAllByPictureNo(Long pictureNo);

    void deleteAllByPictureNo(Long pictureNo);

    void deleteByNo(Long pictureHashtagNo);

    @Query(value = "SELECT count(l.no) as count, p.no as pictureNo, p.image_url as imageUrl, p.height, p.width, u.no as userNo, u.id, u.profile_image_url as profileImageUrl " +
            "FROM oeuvre.picture_hashtag ph LEFT JOIN oeuvre.picture p on ph.picture_no = p.no " +
            "        LEFT JOIN oeuvre.likes l on p.no = l.picture_no " +
            "        LEFT JOIN oeuvre.floor f on p.floor_no = f.no " +
            "        LEFT JOIN oeuvre.user u on f.user_no = u.no " +
            "WHERE u.no not in (SELECT blocked_user_no FROM oeuvre.block WHERE block_user_no = :userNo and block.status = 1) and ph.hashtag_no = :hashtagNo " +
            "and p.status = 1 and f.status = 1 and f.is_public = true " +
            "GROUP BY p.no " +
            "ORDER BY count desc " +
            "LIMIT 7", nativeQuery = true)
    List<GetPopularPicture> getPopularPictures(@Param("userNo") Long userNo, @Param("hashtagNo") Long hashtagNo);
    interface GetPopularPicture {
        Long getPictureNo();
        String getImageUrl();
        Float getHeight();
        Float getWidth();
        Long getUserNo();
        String getId();
        String getProfileImageUrl();
    }
}
