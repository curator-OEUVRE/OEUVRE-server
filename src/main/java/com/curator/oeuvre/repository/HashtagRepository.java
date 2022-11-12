package com.curator.oeuvre.repository;

import com.curator.oeuvre.domain.Hashtag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface HashtagRepository extends JpaRepository <Hashtag, Long> {

    Hashtag findByHashtag(String hashtag);

    Optional<Hashtag> findByNoAndStatus(Long hashtagNo, Integer status);

    Page<Hashtag> findAllByHashtagStartsWithOrderByTagCountDesc(String hashtag, Pageable pageable);

    @Query(value = "SELECT count(*) as count, hashtag.no as hashtagNo, hashtag.hashtag " +
            "FROM oeuvre.hashtag LEFT JOIN oeuvre.picture_hashtag p on hashtag.no = p.hashtag_no " +
            "LEFT JOIN oeuvre.picture on p.picture_no = picture.no " +
            "LEFT JOIN oeuvre.floor on picture.floor_no = floor.no " +
            "WHERE p.status = 1 and floor.is_public = true " +
            "GROUP BY hashtag.no " +
            "ORDER BY count desc " +
            "LIMIT 10", nativeQuery = true)
    List<GetPopularHashtag> getPopularHashtags();
    interface GetPopularHashtag {
        Long getHashtagNo();
        String getHashtag();
    }
}
