package com.curator.oeuvre.repository;

import com.curator.oeuvre.domain.Picture;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PictureRepository extends JpaRepository<Picture, Long> {

    List<Picture> findAllByFloorNoOrderByQueue(Long floorNo);
}
