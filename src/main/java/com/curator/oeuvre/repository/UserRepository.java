package com.curator.oeuvre.repository;

import com.curator.oeuvre.domain.User;
import com.curator.oeuvre.dto.user.response.GetUserSearchResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository <User, Long> {

    Optional<User> findByEmailAndTypeAndStatus(String email, String type, Integer status);

    Optional<User> findByIdAndStatus(String id, Integer status);

    Optional<User> findByNo(Long no);

    Optional<User> findByNoAndStatus(Long userNo, Integer status);

    Page<User> findAllByIdStartsWithAndStatusOrNameContainingAndStatus(String idKeyword, Integer status1, String nameKeyword, Integer status2, Pageable pageable);
}
