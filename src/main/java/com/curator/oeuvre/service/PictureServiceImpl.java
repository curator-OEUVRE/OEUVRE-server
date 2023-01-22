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
    private final PictureHashtagRepository pictureHashtagRepository;
    private final NotificationService notificationService;
    private final NotificationRepository notificationRepository;

    @Override
    @Transactional
    public GetPictureResponseDto getPicture(User user, Long pictureNo) {

        Picture picture = pictureRepository.findByNoAndStatus(pictureNo, 1).orElseThrow(() ->
                new NotFoundException(PICTURE_NOT_FOUND));

        if (Objects.equals(user.getNo(), picture.getFloor().getUser().getNo())) {
            List<Notification> notifications = notificationRepository.findAllByUserNoAndTypeAndLikes_PictureNoAndIsReadAndStatus(
                    user.getNo(), "LIKES", picture.getNo(), false, 1);
            notifications.forEach( notification -> {
                notification.setIsRead(true);
            });
            notificationRepository.saveAll(notifications);
        }

        List<PictureHashtag> pictureHashtags = pictureHashtagRepository.findAllByPictureNo(picture.getNo());
        List<String> hashtags = new ArrayList<String>();
        pictureHashtags.forEach( tag -> {
            hashtags.add(tag.getHashtag().getHashtag());
        });

        return new GetPictureResponseDto(
                picture,
                Objects.equals(user.getNo(), picture.getFloor().getUser().getNo()),
                likesRepository.existsByUserNoAndPictureNo(user.getNo(), picture.getNo()),
                scrapRepository.existsByUserNoAndPictureNo(user.getNo(), picture.getNo()),
                hashtags
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

        if (!Objects.equals(user.getNo(), like.getPicture().getFloor().getUser().getNo()))
            notificationService.postNotification(like.getPicture().getFloor().getUser(), "LIKES", user, null, like, false);
    }

    @Override
    @Transactional
    public void deletePictureLike(User user, Long pictureNo) {

        Picture picture = pictureRepository.findByNoAndStatus(pictureNo, 1).orElseThrow(() ->
                new NotFoundException(PICTURE_NOT_FOUND));

        Likes likes = likesRepository.findByUserNoAndPictureNo(user.getNo(), picture.getNo()).orElseThrow(() ->
                new BadRequestException(LIKE_NOT_FOUND));

        notificationRepository.deleteAllByTypeAndUserNoAndSendUserNoAndLikesNoAndStatus("LIKES", picture.getFloor().getUser().getNo(), user.getNo(), likes.getNo(),1);
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
        picture.setTitle(patchPictureRequestDto.getTitle());
        picture.setManufactureYear(patchPictureRequestDto.getManufactureYear());
        picture.setMaterial(patchPictureRequestDto.getMaterial());
        picture.setScale(patchPictureRequestDto.getScale());
        picture.setDescription(patchPictureRequestDto.getDescription());
        pictureRepository.save(picture);

        floorService.updateHashtag(picture, patchPictureRequestDto.getHashtags());

    }

    @Override
    @Transactional
    public void deletePicture(User user, Long pictureNo) {

        Picture picture = pictureRepository.findByNoAndStatus(pictureNo, 1).orElseThrow(() ->
                new NotFoundException(PICTURE_NOT_FOUND));

        // 접근 권한 확인
        if (!Objects.equals(picture.getFloor().getUser().getNo(), user.getNo())) throw new ForbiddenException(FORBIDDEN_PICTURE);

        pictureHashtagRepository.deleteAllByPictureNo(pictureNo);
        likesRepository.deleteAllByPictureNo(pictureNo);
        scrapRepository.deleteAllByPictureNo(pictureNo);

        List<Picture> otherPictures = pictureRepository.findAllByFloorNoAndStatusAndQueueGreaterThan(picture.getFloor().getNo(), 1, picture.getQueue());
        otherPictures.forEach( otherPicture -> {
            otherPicture.setQueue(otherPicture.getQueue() - 1);
        });
        pictureRepository.saveAll(otherPictures);

        picture.setStatus(0);
        pictureRepository.save(picture);
    }
}
