package com.curator.oeuvre.repository;

import com.curator.oeuvre.domain.Block;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BlockRepository extends JpaRepository<Block, Long> {

    Boolean existsByBlockUserNoAndBlockedUserNoAndStatus(Long blockUserNo, Long blockedUserNo, Integer status);

    void deleteByBlockUserNoAndBlockedUserNoAndStatus(Long blockUserNo, Long blockedUserNo, Integer status);

    List<Block> findAllByBlockUserNoAndStatus(Long blockUserNo, Integer status);
}
