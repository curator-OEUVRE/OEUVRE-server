package com.curator.oeuvre.repository;

import com.curator.oeuvre.domain.Scrap;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScrapRepository extends JpaRepository <Scrap, Long> {

    Boolean existsByUserNoAndPictureNo(Long userNo, Long pictureNo);

    void deleteByUserNoAndPictureNo(Long userNo, Long pictureNo);

}
