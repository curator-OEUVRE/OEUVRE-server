package com.curator.oeuvre.repository;

import com.curator.oeuvre.domain.FcmLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FcmLogRepository extends JpaRepository<FcmLog, Long> {
}
