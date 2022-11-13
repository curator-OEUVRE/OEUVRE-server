package com.curator.oeuvre.repository;

import com.curator.oeuvre.domain.Likes;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface LikesRepository extends JpaRepository <Likes, Long> {

    Boolean existsByUserNoAndPictureNo(Long userNo, Long pictureNo);

    Optional<Likes> findByUserNoAndPictureNo(Long userNo, Long pictureNo);

    void deleteByUserNoAndPictureNo(Long userNo, Long pictureNo);

    List<Likes> findByPictureNoOrderByCreatedAtDesc(Long pictureNo);

    void deleteAllByPictureNo(Long pictureNo);

}
