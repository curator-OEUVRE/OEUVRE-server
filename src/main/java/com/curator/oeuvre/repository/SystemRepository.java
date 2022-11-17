package com.curator.oeuvre.repository;

import com.curator.oeuvre.domain.SystemConstant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SystemRepository extends JpaRepository<SystemConstant, Long> {

    SystemConstant findByNo(Long no);
}
