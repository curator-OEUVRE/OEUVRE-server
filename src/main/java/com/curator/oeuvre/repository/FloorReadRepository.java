package com.curator.oeuvre.repository;

import com.curator.oeuvre.domain.FloorRead;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FloorReadRepository extends JpaRepository <FloorRead, Long> {

    FloorRead findByFloorNoAndUserNoAndStatus(Long floorNo, Long userNo, Integer status);
}
