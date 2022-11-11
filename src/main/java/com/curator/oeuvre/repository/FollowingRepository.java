package com.curator.oeuvre.repository;

import com.curator.oeuvre.domain.Following;
import com.curator.oeuvre.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FollowingRepository extends JpaRepository <Following, Long> {

    Long countFollowingByFollowUserNo(Long followUserNo);

    Long countFollowingByFollowedUserNo(Long followedUserNo);

    Boolean existsByFollowUserNoAndFollowedUserNoAndStatus(Long followUserNo, Long followedUserNo, Integer status);

    void deleteByFollowUserNoAndFollowedUserNoAndStatus(Long followUserNo, Long followedUserNo, Integer status);

    List<Following> findAllByFollowUserNoAndStatus(Long userNo, Integer status);

    List<Following> findAllByFollowedUserNoAndStatus(Long userNo, Integer status);

    void deleteAllByFollowUserNo(Long userNo);

    void deleteAllByFollowedUserNo(Long userNo);
}
