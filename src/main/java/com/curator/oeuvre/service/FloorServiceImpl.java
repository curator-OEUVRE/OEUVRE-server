package com.curator.oeuvre.service;

import com.curator.oeuvre.domain.*;
import com.curator.oeuvre.dto.common.response.PageResponseDto;
import com.curator.oeuvre.dto.floor.request.*;
import com.curator.oeuvre.dto.floor.response.*;
import com.curator.oeuvre.dto.picture.response.GetPictureResponseDto;
import com.curator.oeuvre.exception.BadRequestException;
import com.curator.oeuvre.exception.BaseException;
import com.curator.oeuvre.exception.ForbiddenException;
import com.curator.oeuvre.exception.NotFoundException;
import com.curator.oeuvre.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import static com.curator.oeuvre.constant.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class FloorServiceImpl implements FloorService {

    private final FloorRepository floorRepository;
    private final PictureRepository pictureRepository;
    private final HashtagRepository hashtagRepository;
    private final PictureHashtagRepository pictureHashtagRepository;
    private final FloorReadRepository floorReadRepository;
    private final FollowingRepository followingRepository;
    private final NotificationRepository notificationRepository;
    private final LikesRepository likesRepository;
    private final ScrapRepository scrapRepository;

    @Override
    @Transactional
    public void postHashtag(String hashtag, Picture picture) {

        Hashtag existingHashtag = hashtagRepository.findByHashtag(hashtag);
        if (existingHashtag != null) {  // 이미 존재하는 해시태그
            // 태그
            PictureHashtag pictureHashtag = PictureHashtag.builder()
                    .picture(picture)
                    .hashtag(existingHashtag)
                    .build();
            pictureHashtagRepository.save(pictureHashtag);

            // 태그 수 업데이트
            existingHashtag.setTagCount(existingHashtag.getTagCount() + 1);
            hashtagRepository.save(existingHashtag);

        } else { // 존재하지 않는 해시태그
            // 해시태그 새로 생성
            Hashtag newHashtag = Hashtag.builder()
                    .hashtag(hashtag)
                    .tagCount(1L)
                    .isHead(false)
                    .build();
            hashtagRepository.save(newHashtag);

            // 태그
            PictureHashtag pictureHashtag = PictureHashtag.builder()
                    .picture(picture)
                    .hashtag(newHashtag)
                    .build();
            pictureHashtagRepository.save(pictureHashtag);
        }
    }

    @Override
    @Transactional
    public void updateHashtag(Picture originalPicture, List<String> newHashtags) {

        // 기존 해시태그 엔티티 가져오기
        List<PictureHashtag> originalPictureHashtags = pictureHashtagRepository.findAllByPictureNo(originalPicture.getNo());

        // 기존 해시태그 중 없어진 것 삭제
        originalPictureHashtags.forEach(
                hashtagToRemove -> {
                    if (!newHashtags.contains(hashtagToRemove.getHashtag().getHashtag())) {

                        pictureHashtagRepository.deleteByNo(hashtagToRemove.getNo());
                        hashtagToRemove.getHashtag().setTagCount(hashtagToRemove.getHashtag().getTagCount() - 1);
                        hashtagRepository.save(hashtagToRemove.getHashtag());
                    }
                }
        );
        // 기존 해시태그 문자열 추출
        List<String> originalHashtags = new ArrayList<String>();
        originalPictureHashtags.forEach( tag -> {
            originalHashtags.add(tag.getHashtag().getHashtag());
        });

        newHashtags.forEach(
                newTag -> {
                    // 새로 추가된 해시태그
                    if (!originalHashtags.contains(newTag)) {
                        postHashtag(newTag, originalPicture);
                    }
                }
        );
    }

    @Override
    @Transactional
    public PostFloorResponseDto postFloor(User user, PostFloorRequestDto postFloorRequestDto) {
        // 1. 회원 플로어 개수 조회
        Integer count = floorRepository.countFloorByUserNoAndIsGroupExhibitionAndStatus(user.getNo(), false, 1);

        if (count >= 10) throw new ForbiddenException(TOO_MANY_FLOORS);

        // 2. 플로어 생성
        Floor floor = Floor.builder()
                .user(user)
                .queue(count + 1)
                .name(postFloorRequestDto.getName())
                .description(postFloorRequestDto.getDescription())
                .color(postFloorRequestDto.getColor())
                .gradient(postFloorRequestDto.getGradient())
                .texture(postFloorRequestDto.getTexture())
                .alignment(postFloorRequestDto.getAlignment())
                .isFramed(postFloorRequestDto.getIsFramed())
                .isPublic(postFloorRequestDto.getIsPublic())
                .isCommentAvailable(postFloorRequestDto.getIsCommentAvailable())
                .isGroupExhibition(false)
                .thumbnailNo(0L)
                .build();
        floorRepository.save(floor);

        // 3. 사진 생성
        List<PostFloorPictureDto> pictures = postFloorRequestDto.getPictures();
        if (pictures.size() < 1) throw new ForbiddenException(NO_PICTURES);
        if (pictures.size() > 20) throw new ForbiddenException(TOO_MANY_PICTURES);

        pictures.forEach( pictureDto -> {
            Picture picture = Picture.builder()
                    .floor(floor)
                    .imageUrl(pictureDto.getImageUrl())
                    .smallImageUrl(pictureDto.getSmallImageUrl())
                    .queue(pictureDto.getQueue())
                    .title(pictureDto.getTitle())
                    .manufactureYear(pictureDto.getManufactureYear())
                    .material(pictureDto.getMaterial())
                    .scale(pictureDto.getScale())
                    .description(pictureDto.getDescription())
                    .height(pictureDto.getHeight())
                    .width((pictureDto.getWidth()))
                    .location(pictureDto.getLocation())
                    .build();
            pictureRepository.save(picture);

            // 사진 마다 해시태그 생성
            pictureDto.getHashtags().forEach( hashtag -> {
                postHashtag(hashtag, picture);
            });
            if (pictureDto.getQueue().equals(postFloorRequestDto.getThumbnailQueue())) {
                floor.setThumbnailNo(picture.getNo());
            }
        });

        // 모든 팔로워 읽음 데이터 추가
        List<Following> followers = followingRepository.findAllByFollowedUserNoAndStatus(user.getNo(), 1);
        followers.forEach( follower -> {
            FloorRead floorRead = FloorRead.builder()
                    .floor(floor)
                    .user(follower.getFollowUser())
                    .isNew(true)
                    .isUpdated(false)
                    .updateCount(0)
                    .build();
            floorReadRepository.save(floorRead);
        });

        return new PostFloorResponseDto(floor);
    }

    @Override
    @Transactional
    public GetFloorResponseDto getFloor(User user, Long floorNo) {

        Floor floor = floorRepository.findByNoAndStatus(floorNo, 1).orElseThrow(() ->
                new NotFoundException(FLOOR_NOT_FOUND));
        List<Picture> pictures = pictureRepository.findAllByFloorNoAndStatusOrderByQueue(floorNo, 1);

        // 플로어 읽음 처리
        FloorRead floorRead = floorReadRepository.findByFloorNoAndUserNoAndStatus(floorNo, user.getNo(), 1);
        if (floorRead != null) {
            floorRead.setIsNew(false);
            floorRead.setIsUpdated(false);
            floorRead.setUpdateCount(0);
            floorReadRepository.save(floorRead);
        }

        List<GetPictureResponseDto> pictureDtos = new ArrayList<GetPictureResponseDto>();
        pictures.forEach( picture -> {
            List<PictureHashtag> pictureHashtags = pictureHashtagRepository.findAllByPictureNo(picture.getNo());

            List<String> hashtags = new ArrayList<String>();
            pictureHashtags.forEach( tag -> {
                hashtags.add(tag.getHashtag().getHashtag());
            });
            pictureDtos.add(
                    new GetPictureResponseDto(
                            picture,
                            Objects.equals(user.getNo(), picture.getFloor().getUser().getNo()),
                            likesRepository.existsByUserNoAndPictureNo(user.getNo(), picture.getNo()),
                            scrapRepository.existsByUserNoAndPictureNo(user.getNo(), picture.getNo()),
                            hashtags
                    )
            );
        });
        Boolean hasNewComment;
        if (Objects.equals(user.getNo(), floor.getUser().getNo()))
            hasNewComment = notificationRepository.existsByUserNoAndTypeAndComment_FloorNoAndIsReadAndStatus(user.getNo(), "COMMENT", floor.getNo(), false, 1);
        else hasNewComment = false;

        return new GetFloorResponseDto(
                floor,
                Objects.equals(user.getNo(), floor.getUser().getNo()),
                hasNewComment,
                pictureDtos
        );
    }

    @Override
    @Transactional
    public void patchFloor(User user, Long floorNo, PatchFloorRequestDto patchFloorRequestDto) {

        Floor floor = floorRepository.findByNoAndStatus(floorNo, 1).orElseThrow(() ->
                new NotFoundException(FLOOR_NOT_FOUND));

        // 접근 권한 확인
        if (!Objects.equals(floor.getUser().getNo(), user.getNo())) throw new ForbiddenException(FORBIDDEN_FLOOR);

        // 플로어 정보 업데이트
        floor.setName(patchFloorRequestDto.getName());
        floor.setDescription(patchFloorRequestDto.getDescription());
        floor.setColor(patchFloorRequestDto.getColor());
        floor.setGradient(patchFloorRequestDto.getGradient());
        floor.setTexture(patchFloorRequestDto.getTexture());
        floor.setAlignment(patchFloorRequestDto.getAlignment());
        floor.setIsFramed(patchFloorRequestDto.getIsFramed());
        floor.setIsPublic(patchFloorRequestDto.getIsPublic());
        floor.setIsCommentAvailable(patchFloorRequestDto.getIsCommentAvailable());
        floor.setThumbnailNo(patchFloorRequestDto.getThumbnailNo());
        floorRepository.save(floor);

        List<Long> picturesNos = new ArrayList<Long>();
        patchFloorRequestDto.getPictures().forEach( picture -> {
            picturesNos.add(picture.getPictureNo());
        });

        if (picturesNos.size() < 1) throw new ForbiddenException(NO_PICTURES);
        if (picturesNos.size() > 20) throw new ForbiddenException(TOO_MANY_PICTURES);

        // 기존 사진 중 새로 받은 입력에 포함 되지 않을 경우
        List<Picture> originalPictures = pictureRepository.findAllByFloorNoAndStatusOrderByQueue(floorNo, 1);
        originalPictures.forEach( pictureToRemove -> {
            if (!picturesNos.contains(pictureToRemove.getNo())) {

                // 해당 사진 해시태그 전부 삭제
                List<PictureHashtag> hashtagsToRemove = pictureHashtagRepository.findAllByPictureNo(pictureToRemove.getNo());
                hashtagsToRemove.forEach( tag -> {
                    pictureHashtagRepository.deleteByNo(tag.getNo());
                    tag.getHashtag().setTagCount(tag.getHashtag().getTagCount() - 1);
                    hashtagRepository.save(tag.getHashtag());
                });
                // 사진 삭제
                pictureToRemove.setStatus(0);
            }
        });

        AtomicInteger newPictureCount = new AtomicInteger();
        List<PatchFloorPictureDto> pictures = patchFloorRequestDto.getPictures();
        pictures.forEach( picture -> {
            // 새로 추가 된 사진
            if (picture.getPictureNo() == 0) {
                // 사진 생성
                Picture newPicture = Picture.builder()
                        .floor(floor)
                        .imageUrl(picture.getImageUrl())
                        .smallImageUrl(picture.getSmallImageUrl())
                        .queue(picture.getQueue())
                        .title(picture.getTitle())
                        .manufactureYear(picture.getManufactureYear())
                        .material(picture.getMaterial())
                        .scale(picture.getScale())
                        .description(picture.getDescription())
                        .height(picture.getHeight())
                        .width((picture.getWidth()))
                        .location(picture.getLocation())
                        .build();
                pictureRepository.save(newPicture);
                newPictureCount.getAndIncrement();

                // 사진 마다 해시태그 생성
                picture.getHashtags().forEach( hashtag -> {
                    postHashtag(hashtag, newPicture);
                });

            } else {
                // 기존 사진 업데이트
                Picture originalPicture = originalPictures.stream()
                        .filter( pic -> picture.getPictureNo().equals(pic.getNo()))
                        .findAny()
                        .orElse(null);
                if (originalPicture == null) throw new BadRequestException(PICTURE_NOT_FOUND);

                originalPicture.setTitle(picture.getTitle());
                originalPicture.setManufactureYear(picture.getManufactureYear());
                originalPicture.setMaterial(picture.getMaterial());
                originalPicture.setScale(picture.getScale());
                originalPicture.setDescription(picture.getDescription());
                originalPicture.setQueue(picture.getQueue());
                originalPicture.setHeight(picture.getHeight());
                originalPicture.setLocation(picture.getLocation());
                originalPicture.setWidth(picture.getWidth());

                updateHashtag(originalPicture, picture.getHashtags());
            }
        });
        pictureRepository.saveAll(originalPictures);

        // 모든 팔로워 읽음 데이터 업데이트
        if (newPictureCount.get() > 0) {
            List<Following> followers = followingRepository.findAllByFollowedUserNoAndStatus(user.getNo(), 1);
            followers.forEach( follower -> {
                FloorRead floorRead = floorReadRepository.findByFloorNoAndUserNoAndStatus(floorNo, follower.getFollowUser().getNo(), 1);

                if (floorRead == null) {
                    FloorRead newFloorRead = FloorRead.builder()
                            .floor(floor)
                            .user(follower.getFollowUser())
                            .isNew(false)
                            .isUpdated(true)
                            .updateCount(newPictureCount.get())
                            .build();
                    floorReadRepository.save(newFloorRead);

                } else if (!floorRead.getIsNew()) {
                    floorRead.setIsUpdated(true);
                    floorRead.setUpdateCount(floorRead.getUpdateCount() + newPictureCount.get());
                    floorReadRepository.save(floorRead);
                }
            });
        }
    }

    @Override
    @Transactional
    public void patchFloorQueue(User user, List<PatchFloorQueueRequestDto> patchFloorQueueRequestDto) {

        patchFloorQueueRequestDto.forEach( dto -> {
            Floor floor = floorRepository.findByNoAndStatus(dto.getFloorNo(), 1).orElseThrow(() ->
                    new NotFoundException(FLOOR_NOT_FOUND));
            if (!Objects.equals(floor.getUser().getNo(), user.getNo())) throw new ForbiddenException(FORBIDDEN_FLOOR);

            floor.setQueue(dto.getQueue());
            floorRepository.save(floor);
        });
    }

    public PageResponseDto<List<GetHomeFloorResponseDto>> getHomeFloorsByFollowing(User user, Integer page, Integer size) {
        Pageable pageRequest = PageRequest.of(page, size);

        Page<FloorRepository.GetHomeFloor> floors = floorRepository.findHomeFloorsByFollowing(user.getNo(), pageRequest);

        List<GetHomeFloorResponseDto> result = new ArrayList<>();
        floors.forEach( floor -> {
            result.add(new GetHomeFloorResponseDto(
                    floor.getFloorNo(),
                    floor.getFloorName(),
                    floor.getFloorDescription(),
                    floor.getQueue(),
                    floor.getExhibitionName(),
                    floor.getThumbnailUrl(),
                    floor.getSmallThumbnailUrl(),
                    floor.getHeight(),
                    floor.getWidth(),
                    floor.getUserNo(),
                    floor.getId(),
                    floor.getProfileImageUrl(),
                    floor.getIsNew() == 1,
                    floor.getIsUpdated() == 1,
                    floor.getUpdateCount(),
                    floor.getIsMine() == 1,
                    floor.getUpdatedAt()
            ));
        });
        return new PageResponseDto<>(floors.isLast(), result);
    }

    public PageResponseDto<List<GetHomeFloorResponseDto>> getHomeFloorsByRecent(User user, Integer page, Integer size) {
        Pageable pageRequest = PageRequest.of(page, size);

        Page<FloorRepository.GetHomeFloor> floors = floorRepository.findHomeFloorsByRecent(user.getNo(), pageRequest);

        List<GetHomeFloorResponseDto> result = new ArrayList<>();
        floors.forEach( floor -> {
            result.add(new GetHomeFloorResponseDto(
                    floor.getFloorNo(),
                    floor.getFloorName(),
                    floor.getFloorDescription(),
                    floor.getQueue(),
                    floor.getExhibitionName(),
                    floor.getThumbnailUrl(),
                    floor.getSmallThumbnailUrl(),
                    floor.getHeight(),
                    floor.getWidth(),
                    floor.getUserNo(),
                    floor.getId(),
                    floor.getProfileImageUrl(),
                    null,
                    null,
                  null,
                    floor.getUserNo().equals(user.getNo()),
                    floor.getUpdatedAt()
            ));
        });
        return new PageResponseDto<>(floors.isLast(), result);
    }

    @Override
    @Transactional
    public PageResponseDto<List<GetHomeFloorResponseDto>> getHomeFloors(User user, String view, Integer page, Integer size) {

        if (view.equals("following")) {
            return this.getHomeFloorsByFollowing(user, page, size);
        } else {
            return this.getHomeFloorsByRecent(user, page, size);
        }
    }

    @Override
    public PageResponseDto<List<GetFloorSearchResponseDto>> searchFloors(User user, String keyword, Integer page, Integer size) {

        Pageable pageRequest = PageRequest.of(page, size);

        Page<FloorRepository.SearchFloor> floors = floorRepository.searchFloors(user.getNo(), keyword, pageRequest);

        List<GetFloorSearchResponseDto> result = new ArrayList<>();
        floors.forEach(floor -> {
            result.add(new GetFloorSearchResponseDto(
                    floor.getFloorNo(),
                    floor.getFloorName(),
                    floor.getExhibitionName(),
                    floor.getThumbnailUrl(),
                    floor.getSmallThumbnailUrl(),
                    floor.getHeight(),
                    floor.getWidth()
            ));
        });
        return new PageResponseDto<>(floors.isLast(), result);
    }

    @Override
    @Transactional
    public void deleteFloor(User user, Long floorNo) {

        Floor floor = floorRepository.findByNoAndStatus(floorNo, 1).orElseThrow(() ->
                new NotFoundException(FLOOR_NOT_FOUND));

        // 접근 권한 확인
        if (!Objects.equals(floor.getUser().getNo(), user.getNo())) throw new ForbiddenException(FORBIDDEN_FLOOR);

        List<Picture> pictures = pictureRepository.findAllByFloorNoAndStatusOrderByQueue(floorNo, 1);
        pictures.forEach( picture -> {
            picture.setStatus(0);
        });
        pictureRepository.saveAll(pictures);

        List<Floor> otherFloors = floorRepository.findAllByUserNoAndStatusAndQueueGreaterThan(user.getNo(), 1, floor.getQueue());
        otherFloors.forEach( otherFloor -> {
            otherFloor.setQueue(otherFloor.getQueue() - 1);
        });
        floorRepository.saveAll(otherFloors);

        floor.setStatus(0);
        floorRepository.save(floor);
    }
}
