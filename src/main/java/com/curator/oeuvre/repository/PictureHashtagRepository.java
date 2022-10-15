package com.curator.oeuvre.repository;

import com.curator.oeuvre.domain.Hashtag;
import com.curator.oeuvre.domain.PictureHashtag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PictureHashtagRepository extends JpaRepository <PictureHashtag, Long> {

    List<PictureHashtag> findAllByPictureNo(Long picureNo);
}
