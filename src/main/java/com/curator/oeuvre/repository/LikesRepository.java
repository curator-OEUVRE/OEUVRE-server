package com.curator.oeuvre.repository;

import com.curator.oeuvre.domain.Likes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikesRepository extends JpaRepository <Likes, Long> {

    Boolean existsByUserNoAndPictureNo(Long userNo, Long pictureNo);
}
