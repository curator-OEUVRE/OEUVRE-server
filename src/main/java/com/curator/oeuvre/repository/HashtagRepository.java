package com.curator.oeuvre.repository;

import com.curator.oeuvre.domain.Hashtag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HashtagRepository extends JpaRepository <Hashtag, Long> {

    Hashtag findByHashtag(String hashtag);

    Hashtag findByNo(Long hashtagNo);

}
