package com.curator.oeuvre.service;

import com.curator.oeuvre.domain.*;
import com.curator.oeuvre.dto.floor.request.PostFloorPictureDto;
import com.curator.oeuvre.dto.floor.request.PostFloorRequestDto;
import com.curator.oeuvre.dto.floor.response.GetFloorPictureDto;
import com.curator.oeuvre.dto.floor.response.GetFloorResponseDto;
import com.curator.oeuvre.dto.floor.response.PostFloorResponseDto;
import com.curator.oeuvre.exception.NotFoundException;
import com.curator.oeuvre.repository.FloorRepository;
import com.curator.oeuvre.repository.HashtagRepository;
import com.curator.oeuvre.repository.PictureHashtagRepository;
import com.curator.oeuvre.repository.PictureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;
import static com.curator.oeuvre.constant.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class FloorServiceImpl implements FloorService {

    private final FloorRepository floorRepository;
    private final PictureRepository pictureRepository;
    private final HashtagRepository hashtagRepository;
    private final PictureHashtagRepository pictureHashtagRepository;

    @Override
    @Transactional
    public PostFloorResponseDto postFloor(User user, PostFloorRequestDto postFloorRequestDto) {
        // 1. 회원 플로어 개수 조회
        Integer count = floorRepository.countFloorByUser(user);

        // 2. 플로어 생성
        Floor floor = Floor.builder()
                .user(user)
                .queue(count + 1)
                .name(postFloorRequestDto.getName())
                .color(postFloorRequestDto.getColor())
                .texture(postFloorRequestDto.getTexture())
                .isPublic(postFloorRequestDto.getIsPublic())
                .isCommentAvailable(postFloorRequestDto.getIsCommentAvailable())
                .isGroupExhibition(false)
                .build();
        floorRepository.save(floor);

        // 3. 사진 생성
        List<PostFloorPictureDto> pictures = postFloorRequestDto.getPictures();
        pictures.forEach( pictureDto -> {
            Picture picture = Picture.builder()
                    .floor(floor)
                    .imageUrl(pictureDto.getImageUrl())
                    .queue(pictureDto.getQueue())
                    .description(pictureDto.getDescription())
                    .height(pictureDto.getHeight())
                    .width((pictureDto.getWidth()))
                    .location(pictureDto.getLocation())
                    .build();
            pictureRepository.save(picture);

            // 사진 마다 해시태그 생성
            pictureDto.getHashtags().forEach( hashtag -> {
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
                            .build();
                    hashtagRepository.save(newHashtag);

                    // 태그
                    PictureHashtag pictureHashtag = PictureHashtag.builder()
                            .picture(picture)
                            .hashtag(newHashtag)
                            .build();
                    pictureHashtagRepository.save(pictureHashtag);
                }
            });
        });
        return new PostFloorResponseDto(floor.getNo());
    }

    @Override
    public GetFloorResponseDto getFloor(User user, Long floorNo) {

        Floor floor = floorRepository.findByNo(floorNo).orElseThrow(() ->
                new NotFoundException(FLOOR_NOT_FOUND));
        List<Picture> pictures = pictureRepository.findAllByFloorNoOrderByQueue(floorNo);

        List<GetFloorPictureDto> pictureDtos = new ArrayList<GetFloorPictureDto>();
        pictures.forEach( picture -> {
            List<PictureHashtag> pictureHashtags = pictureHashtagRepository.findAllByPictureNo(picture.getNo());

            List<String> hashtags = new ArrayList<String>();
            pictureHashtags.forEach( tag -> {
                Hashtag hashtag = hashtagRepository.findByNo(tag.getHashtag().getNo());
                hashtags.add(hashtag.getHashtag());
            });

            pictureDtos.add(
                    new GetFloorPictureDto(
                            picture.getNo(),
                            picture.getQueue(),
                            picture.getImageUrl(),
                            picture.getDescription(),
                            picture.getHeight(),
                            picture.getWidth(),
                            picture.getLocation(),
                            hashtags
                    )
            );
        });

        return new GetFloorResponseDto(
                floor.getNo(),
                floor.getUser().getNo(),
                floor.getName(),
                floor.getColor(),
                floor.getTexture(),
                floor.getIsPublic(),
                floor.getIsCommentAvailable(),
                Objects.equals(user.getNo(), floor.getUser().getNo()),
                pictureDtos
        );
    }
}
