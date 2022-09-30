package com.curator.oeuvre.repository;

import com.curator.oeuvre.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Long> {

    Optional<Users> findByEmailAndType(String email, String type);
    Optional<Users> findById(String id);

}
