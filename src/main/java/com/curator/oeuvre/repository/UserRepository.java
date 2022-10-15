package com.curator.oeuvre.repository;

import com.curator.oeuvre.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository <User, Long> {

    Optional<User> findByEmailAndType(String email, String type);

    Optional<User> findById(String id);

    Optional<User> findByNo(Long no);

}
