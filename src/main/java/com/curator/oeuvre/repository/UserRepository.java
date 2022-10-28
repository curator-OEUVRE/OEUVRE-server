package com.curator.oeuvre.repository;

import com.curator.oeuvre.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository <User, Long> {

    Optional<User> findByEmailAndTypeAndStatus(String email, String type, Integer status);

    Optional<User> findById(String id);

    Optional<User> findByNo(Long no);

    Optional<User> findByNoAndStatus(Long userNo, Integer status);

}
