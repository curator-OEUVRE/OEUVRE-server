package com.curator.oeuvre.service;

import com.curator.oeuvre.domain.*;
import com.curator.oeuvre.dto.picture.request.PatchPictureRequestDto;
import com.curator.oeuvre.dto.picture.response.GetPictureResponseDto;
import com.curator.oeuvre.dto.picture.response.GetPictureLikeUserResponseDto;
import com.curator.oeuvre.exception.BadRequestException;
import com.curator.oeuvre.exception.ForbiddenException;
import com.curator.oeuvre.exception.NotFoundException;
import com.curator.oeuvre.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import static com.curator.oeuvre.constant.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class PictureServiceImpl implements PictureService{

    private final PictureRepository pictureRepository;
    private final LikesRepository likesRepository;
    private final ScrapRepository scrapRepository;
    private final FloorService floorService;

    @Override
    public GetPictureResponseDto getPicture(User user, Long pictureNo) {

        Picture picture = pictureRepository.findByNoAndStatus(pictureNo, 1).orElseThrow(() ->
                new NotFoundException(PICTURE_NOT_FOUND));

        return new GetPictureResponseDto(
                picture,
                Objects.equals(user.getNo(), picture.getFloor().getUser().getNo()),
                likesRepository.existsByUserNoAndPictureNo(user.getNo(), picture.getNo()),
                scrapRepository.existsByUserNoAndPictureNo(user.getNo(), picture.getNo())
        );
    }

    @Override
    public void postPictureLike(User user, Long pictureNo) {

        Picture picture = pictureRepository.findByNoAndStatus(pictureNo, 1).orElseThrow(() ->
                new NotFoundException(PICTURE_NOT_FOUND));

        if (likesRepository.existsByUserNoAndPictureNo(user.getNo(), picture.getNo()))
            throw new BadRequestException(ALREADY_LIKED);

        Likes like = Likes.builder()
                .picture(picture)
                .user(user)
                .build();
        likesRepository.save(like);
    }

    @Override
    @Transactional
    public void deletePictureLike(User user, Long pictureNo) {

        Picture picture = pictureRepository.findByNoAndStatus(pictureNo, 1).orElseThrow(() ->
                new NotFoundException(PICTURE_NOT_FOUND));

        if (!likesRepository.existsByUserNoAndPictureNo(user.getNo(), picture.getNo()))
            throw new BadRequestException(LIKE_NOT_FOUND);

        likesRepository.deleteByUserNoAndPictureNo(user.getNo(), picture.getNo());
    }

    @Override
    public void postPictureScrap(User user, Long pictureNo) {

        Picture picture = pictureRepository.findByNoAndStatus(pictureNo, 1).orElseThrow(() ->
                new NotFoundException(PICTURE_NOT_FOUND));

        if (scrapRepository.existsByUserNoAndPictureNo(user.getNo(), picture.getNo()))
            throw new BadRequestException(ALREADY_SCRAPED);

        Scrap scrap = Scrap.builder()
                .picture(picture)
                .user(user)
                .build();
        scrapRepository.save(scrap);
    }

    @Override
    @Transactional
    public void deletePictureScrap(User user, Long pictureNo) {

        Picture picture = pictureRepository.findByNoAndStatus(pictureNo, 1).orElseThrow(() ->
                new NotFoundException(PICTURE_NOT_FOUND));

        if (!scrapRepository.existsByUserNoAndPictureNo(user.getNo(), picture.getNo()))
            throw new BadRequestException(SCRAP_NOT_FOUND);

        scrapRepository.deleteByUserNoAndPictureNo(user.getNo(), picture.getNo());
    }

    @Override
    public List<GetPictureLikeUserResponseDto> getPictureLikeUsers(Long pictureNo) {

        Picture picture = pictureRepository.findByNoAndStatus(pictureNo, 1).orElseThrow(() ->
                new NotFoundException(PICTURE_NOT_FOUND));

        List<Likes> likes = likesRepository.findByPictureNoOrderByCreatedAtDesc(picture.getNo());

        List<GetPictureLikeUserResponseDto> result = new ArrayList<>();
        likes.forEach( like -> {
            User user = like.getUser();
            result.add(new GetPictureLikeUserResponseDto(user));
        } );
        return result;
    }

    @Override
    @Transactional
    public void patchPictureDescription(User user, Long pictureNo, PatchPictureRequestDto patchPictureRequestDto) {

        Picture picture = pictureRepository.findByNoAndStatus(pictureNo, 1).orElseThrow(() ->
                new NotFoundException(PICTURE_NOT_FOUND));

        // 접근 권한 확인
        if (!Objects.equals(picture.getFloor().getUser().getNo(), user.getNo())) throw new ForbiddenException(FORBIDDEN_PICTURE);

        // 설명 업데이트
        picture.setDescription(patchPictureRequestDto.getDescription());
        pictureRepository.save(picture);

        floorService.updateHashtag(picture, patchPictureRequestDto.getHashtags());

    }
}
