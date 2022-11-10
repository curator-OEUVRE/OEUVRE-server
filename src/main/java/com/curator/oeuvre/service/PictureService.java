package com.curator.oeuvre.service;

import com.curator.oeuvre.domain.User;
import com.curator.oeuvre.dto.picture.request.PatchPictureRequestDto;
import com.curator.oeuvre.dto.picture.response.GetPictureResponseDto;
import com.curator.oeuvre.dto.picture.response.GetPictureLikeUserResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PictureService {

    GetPictureResponseDto getPicture(User user, Long pictureNo);

    void postPictureLike(User user, Long pictureNo);

    void deletePictureLike(User user, Long pictureNo);

    void postPictureScrap(User user, Long pictureNo);

    void deletePictureScrap(User user, Long pictureNo);

    List<GetPictureLikeUserResponseDto> getPictureLikeUsers(Long pictureNo);

    void patchPictureDescription(User user, Long pictureNo, PatchPictureRequestDto patchPictureRequestDto);
}
