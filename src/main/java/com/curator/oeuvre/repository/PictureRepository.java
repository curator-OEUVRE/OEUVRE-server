package com.curator.oeuvre.repository;

import com.curator.oeuvre.domain.Picture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PictureRepository extends JpaRepository<Picture, Long> {

}
