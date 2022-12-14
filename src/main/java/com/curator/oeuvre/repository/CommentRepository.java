package com.curator.oeuvre.repository;

import com.curator.oeuvre.domain.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentRepository extends JpaRepository <Comment, Long> {

    Optional<Comment> findByNoAndStatus(Long commentNo, Integer status);

    void deleteByNo(Long commentNo);

    Page<Comment> findAllByFloorNoAndStatusOrderByCreatedAtAsc(Long floorNo, Integer status, Pageable pageable);
}
