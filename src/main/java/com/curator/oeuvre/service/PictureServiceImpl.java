package com.curator.oeuvre.service;

import com.curator.oeuvre.domain.Likes;
import com.curator.oeuvre.domain.Picture;
import com.curator.oeuvre.domain.Scrap;
import com.curator.oeuvre.domain.User;
import com.curator.oeuvre.dto.picture.response.GetPictureResponseDto;
import com.curator.oeuvre.dto.user.response.GetPictureLikeUserResponseDto;
import com.curator.oeuvre.exception.BadRequestException;
import com.curator.oeuvre.exception.NotFoundException;
import com.curator.oeuvre.repository.LikesRepository;
import com.curator.oeuvre.repository.PictureRepository;
import com.curator.oeuvre.repository.ScrapRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.curator.oeuvre.constant.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class PictureServiceImpl implements PictureService{

    private final PictureRepository pictureRepository;
    private final LikesRepository likesRepository;
    private final ScrapRepository scrapRepository;

    @Override
    public GetPictureResponseDto getPicture(User user, Long pictureNo) {

        Picture picture = pictureRepository.findByNo(pictureNo).orElseThrow(() ->
                new NotFoundException(PICTURE_NOT_FOUND));

        return new GetPictureResponseDto(
                picture.getNo(),
                picture.getFloor().getNo(),
                picture.getImageUrl(),
                picture.getDescription(),
                Objects.equals(user.getNo(), picture.getFloor().getUser().getNo()),
                likesRepository.existsByUserNoAndPictureNo(user.getNo(), picture.getNo()),
                scrapRepository.existsByUserNoAndPictureNo(user.getNo(), picture.getNo())
        );
    }

    @Override
    public Void postPictureLike(User user, Long pictureNo) {

        Picture picture = pictureRepository.findByNo(pictureNo).orElseThrow(() ->
                new NotFoundException(PICTURE_NOT_FOUND));

        if (likesRepository.existsByUserNoAndPictureNo(user.getNo(), picture.getNo()))
            throw new BadRequestException(ALREADY_LIKED);

        Likes like = Likes.builder()
                .picture(picture)
                .user(user)
                .build();
        likesRepository.save(like);
        return null;
    }

    @Override
    @Transactional
    public Void deletePictureLike(User user, Long pictureNo) {

        Picture picture = pictureRepository.findByNo(pictureNo).orElseThrow(() ->
                new NotFoundException(PICTURE_NOT_FOUND));

        if (!likesRepository.existsByUserNoAndPictureNo(user.getNo(), picture.getNo()))
            throw new BadRequestException(LIKE_NOT_FOUND);

        likesRepository.deleteByUserNoAndPictureNo(user.getNo(), picture.getNo());
        return null;
    }

    @Override
    public Void postPictureScrap(User user, Long pictureNo) {

        Picture picture = pictureRepository.findByNo(pictureNo).orElseThrow(() ->
                new NotFoundException(PICTURE_NOT_FOUND));

        if (scrapRepository.existsByUserNoAndPictureNo(user.getNo(), picture.getNo()))
            throw new BadRequestException(ALREADY_SCRAPED);

        Scrap scrap = Scrap.builder()
                .picture(picture)
                .user(user)
                .build();
        scrapRepository.save(scrap);
        return null;
    }

    @Override
    @Transactional
    public Void deletePictureScrap(User user, Long pictureNo) {

        Picture picture = pictureRepository.findByNo(pictureNo).orElseThrow(() ->
                new NotFoundException(PICTURE_NOT_FOUND));

        if (!scrapRepository.existsByUserNoAndPictureNo(user.getNo(), picture.getNo()))
            throw new BadRequestException(SCRAP_NOT_FOUND);

        scrapRepository.deleteByUserNoAndPictureNo(user.getNo(), picture.getNo());
        return null;
    }

    @Override
    public List<GetPictureLikeUserResponseDto> getPictureLikeUsers(Long pictureNo) {

        Picture picture = pictureRepository.findByNo(pictureNo).orElseThrow(() ->
                new NotFoundException(PICTURE_NOT_FOUND));

        List<Likes> likes = likesRepository.findByPictureNoOrderByCreatedAtDesc(pictureNo);

        List<GetPictureLikeUserResponseDto> result = new ArrayList<>();
        likes.forEach( like -> {
            User user = like.getUser();
            result.add(new GetPictureLikeUserResponseDto(user.getNo(), user.getProfileImageUrl(), user.getId(), user.getName()));
        } );
        return result;
    }
}
