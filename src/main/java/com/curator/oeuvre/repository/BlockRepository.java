package com.curator.oeuvre.repository;

import com.curator.oeuvre.domain.Block;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlockRepository extends JpaRepository<Block, Long> {

    Boolean existsByBlockUserNoAndBlockedUserNoAndStatus(Long blockUserNo, Long blockedUserNo, Integer status);

    void deleteByBlockUserNoAndBlockedUserNoAndStatus(Long blockUserNo, Long blockedUserNo, Integer status);
}
