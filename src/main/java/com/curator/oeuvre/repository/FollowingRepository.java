package com.curator.oeuvre.repository;

import com.curator.oeuvre.domain.Following;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowingRepository extends JpaRepository <Following, Long> {

    Long countFollowingByFollowUserNo(Long followUserNo);

    Long countFollowingByFollowedUserNo(Long followedUserNo);
}
