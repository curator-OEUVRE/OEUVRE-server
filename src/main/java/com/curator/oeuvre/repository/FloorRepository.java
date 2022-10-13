package com.curator.oeuvre.repository;

import com.curator.oeuvre.domain.Floor;
import com.curator.oeuvre.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FloorRepository extends JpaRepository<Floor, Long> {

    Integer countFloorByUser(User user);
}
