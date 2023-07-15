package com.curator.oeuvre.repository;

import com.curator.oeuvre.domain.PictureHashtag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PictureHashtagRepository extends JpaRepository <PictureHashtag, Long> {

    List<PictureHashtag> findAllByPictureNo(Long pictureNo);

    void deleteAllByPictureNo(Long pictureNo);

    void deleteByNo(Long pictureHashtagNo);

    @Query(value = "SELECT count(l.no) as count, p.no as pictureNo, p.image_url as imageUrl, p.title, p.manufacture_year as manufactureYear, p.material, p.scale, p.description, " +
            " p.height, p.width, u.no as userNo, u.id, u.profile_image_url as profileImageUrl " +
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
        String getTitle();
        String getManufactureYear();
        String getMaterial();
        String getScale();
        String getDescription();
        Float getHeight();
        Float getWidth();
        Long getUserNo();
        String getId();
        String getProfileImageUrl();
    }

    @Query(value = "SELECT distinct p.no as pictureNo, p.image_url as imageUrl, p.title, p.manufacture_year as manufactureYear, p.material, p.scale, p.description, p.height, p.width, " +
            "       user.no as userNo, user.id, user.profile_image_url as profileImageUrl, " +
            "       (SELECT count(likes.no) FROM oeuvre.picture LEFT JOIN oeuvre.likes ON likes.picture_no = p.no and likes.status = 1 WHERE picture.status = 1) as count " +
            "FROM oeuvre.picture p " +
            "    LEFT JOIN oeuvre.picture_hashtag ph ON p.no = ph.picture_no " +
            "    LEFT JOIN oeuvre.floor ON p.floor_no = floor.no " +
            "    LEFT JOIN oeuvre.user ON floor.user_no = user.no " +
            "WHERE ph.hashtag_no = :hashtagNo and floor.status = 1 and floor.is_public = true and " +
            "      p.status = 1 and ph.status = 1 and user.status = 1 and " +
            "      user.no not in (SELECT blocked_user_no FROM oeuvre.block WHERE block_user_no = :userNo) " +
            "ORDER BY count desc, p.created_at desc ",
            countQuery = "SELECT count(*) FROM (SELECT distinct p.no as pictureNo, p.image_url as imageUrl, p.height, p.width, " +
                    "       user.no as userNo, user.id, user.profile_image_url as profileImageUrl, " +
                    "       (SELECT count(likes.no) FROM oeuvre.picture LEFT JOIN oeuvre.likes ON likes.picture_no = p.no and likes.status = 1 WHERE picture.status = 1) as count " +
                    "FROM oeuvre.picture p " +
                    "    LEFT JOIN oeuvre.picture_hashtag ph ON p.no = ph.picture_no " +
                    "    LEFT JOIN oeuvre.floor ON p.floor_no = floor.no " +
                    "    LEFT JOIN oeuvre.user ON floor.user_no = user.no " +
                    "WHERE ph.hashtag_no = :hashtagNo and floor.status = 1 and floor.is_public = true and " +
                    "      p.status = 1 and ph.status = 1 and user.status = 1 and " +
                    "      user.no not in (SELECT blocked_user_no FROM oeuvre.block WHERE block_user_no = :userNo) " +
                    "ORDER BY count desc, p.created_at desc) as c", nativeQuery = true)
    Page<GetHashtagPicture> findAllByHashtagNoSortByPopular(@Param("hashtagNo") Long hashtagNo, @Param("userNo") Long userNo, Pageable pageable);

    @Query(value = "SELECT distinct p.no as pictureNo, p.image_url as imageUrl, p.title, p.manufacture_year as manufactureYear, p.material, p.scale, p.description, p.height, p.width, " +
            "       user.no as userNo, user.id, user.profile_image_url as profileImageUrl " +
            "FROM oeuvre.picture p " +
            "    LEFT JOIN oeuvre.picture_hashtag ph ON p.no = ph.picture_no " +
            "    LEFT JOIN oeuvre.floor ON p.floor_no = floor.no " +
            "    LEFT JOIN oeuvre.user ON floor.user_no = user.no " +
            "WHERE ph.hashtag_no = :hashtagNo and floor.status = 1 and floor.is_public = true and " +
            "      p.status = 1 and ph.status = 1 and user.status = 1 and " +
            "      user.no not in (SELECT blocked_user_no FROM oeuvre.block WHERE block_user_no = :userNo) " +
            "ORDER BY p.created_at desc",
            countQuery = "SELECT count(*) FROM (SELECT distinct p.no FROM oeuvre.picture p " +
            "    LEFT JOIN oeuvre.picture_hashtag ph ON p.no = ph.picture_no " +
                    "    LEFT JOIN oeuvre.floor ON p.floor_no = floor.no " +
                    "    LEFT JOIN oeuvre.user ON floor.user_no = user.no " +
                    "WHERE ph.hashtag_no = :hashtagNo and floor.status = 1 and floor.is_public = true and " +
                    "      p.status = 1 and ph.status = 1 and user.status = 1 and " +
                    "      user.no not in (SELECT blocked_user_no FROM oeuvre.block WHERE block_user_no = :userNo)) as c", nativeQuery = true)
    Page<GetHashtagPicture> findAllByHashtagNoSortByRecent(@Param("hashtagNo") Long hashtagNo, @Param("userNo") Long userNo, Pageable pageable);

    interface GetHashtagPicture {
        Long getPictureNo();
        String getImageUrl();
        String getTitle();
        String getManufactureYear();
        String getMaterial();
        String getScale();
        String getDescription();
        Float getHeight();
        Float getWidth();
        Long getUserNo();
        String getId();
        String getProfileImageUrl();
    }
}
