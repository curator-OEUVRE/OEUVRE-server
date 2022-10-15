package com.curator.oeuvre.service;

import com.curator.oeuvre.domain.User;
import com.curator.oeuvre.dto.picture.response.GetPictureResponseDto;
import org.springframework.stereotype.Service;

@Service
public interface PictureService {

    GetPictureResponseDto getPicture(User user, Long pictureNo);
}
