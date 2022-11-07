package com.curator.oeuvre.repository;

import com.curator.oeuvre.domain.Hashtag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HashtagRepository extends JpaRepository <Hashtag, Long> {

    Hashtag findByHashtag(String hashtag);

    Hashtag findByNo(Long hashtagNo);

    Page<Hashtag> findAllByHashtagStartsWithOrderByTagCountDesc(String hashtag, Pageable pageable);

    @Query(value = "SELECT count(*) as count, hashtag.no as hashtagNo, hashtag.hashtag " +
            "FROM oeuvre.hashtag LEFT JOIN oeuvre.picture_hashtag p on hashtag.no = p.hashtag_no " +
            "WHERE p.status = 1 " +
            "GROUP BY hashtag.no " +
            "ORDER BY count desc " +
            "LIMIT 10", nativeQuery = true)
    List<GetPopularHashtag> getPopularHashtags();
    interface GetPopularHashtag {
        Long getHashtagNo();
        String getHashtag();
    }
}
