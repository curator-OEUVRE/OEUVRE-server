package com.curator.oeuvre.service;

import com.curator.oeuvre.domain.User;
import com.curator.oeuvre.dto.picture.response.GetPictureResponseDto;
import com.curator.oeuvre.dto.picture.response.GetPictureLikeUserResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PictureService {

    GetPictureResponseDto getPicture(User user, Long pictureNo);

    Void postPictureLike(User user, Long pictureNo);

    Void deletePictureLike(User user, Long pictureNo);

    Void postPictureScrap(User user, Long pictureNo);

    Void deletePictureScrap(User user, Long pictureNo);

    List<GetPictureLikeUserResponseDto> getPictureLikeUsers(Long pictureNo);

    Void patchPictureDescription(User user, Long pictureNo);
}
