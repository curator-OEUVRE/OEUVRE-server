package com.curator.oeuvre.repository;

import com.curator.oeuvre.domain.Picture;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PictureRepository extends JpaRepository<Picture, Long> {

    List<Picture> findAllByFloorNoAndStatusOrderByQueue(Long floorNo, Integer status);

    List<Picture> findTop7ByFloorNoAndStatusOrderByQueue(Long floorNo, Integer status);

    Optional<Picture> findByNoAndStatus(Long pictureNo, Integer status);

    List<Picture> findAllByFloorNoAndStatusAndQueueGreaterThan(Long floorNo, Integer status, Integer queue);
}
