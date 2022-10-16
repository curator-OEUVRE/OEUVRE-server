package com.curator.oeuvre.service;

import com.curator.oeuvre.domain.User;
import com.curator.oeuvre.dto.picture.response.GetPictureResponseDto;
import org.springframework.stereotype.Service;

@Service
public interface PictureService {

    GetPictureResponseDto getPicture(User user, Long pictureNo);

    Void postPictureLike(User user, Long pictureNo);

    Void deletePictureLike(User user, Long pictureNo);

    Void postPictureScrap(User user, Long pictureNo);

    Void deletePictureScrap(User user, Long pictureNo);
}
