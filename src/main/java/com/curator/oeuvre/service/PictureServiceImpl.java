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
    private final PictureHashtagRepository pictureHashtagRepository;
    private final HashtagRepository hashtagRepository;

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
    public void postPictureLike(User user, Long pictureNo) {

        Picture picture = pictureRepository.findByNo(pictureNo).orElseThrow(() ->
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

        Picture picture = pictureRepository.findByNo(pictureNo).orElseThrow(() ->
                new NotFoundException(PICTURE_NOT_FOUND));

        if (!likesRepository.existsByUserNoAndPictureNo(user.getNo(), picture.getNo()))
            throw new BadRequestException(LIKE_NOT_FOUND);

        likesRepository.deleteByUserNoAndPictureNo(user.getNo(), picture.getNo());
    }

    @Override
    public void postPictureScrap(User user, Long pictureNo) {

        Picture picture = pictureRepository.findByNo(pictureNo).orElseThrow(() ->
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

        Picture picture = pictureRepository.findByNo(pictureNo).orElseThrow(() ->
                new NotFoundException(PICTURE_NOT_FOUND));

        if (!scrapRepository.existsByUserNoAndPictureNo(user.getNo(), picture.getNo()))
            throw new BadRequestException(SCRAP_NOT_FOUND);

        scrapRepository.deleteByUserNoAndPictureNo(user.getNo(), picture.getNo());
    }

    @Override
    public List<GetPictureLikeUserResponseDto> getPictureLikeUsers(Long pictureNo) {

        Picture picture = pictureRepository.findByNo(pictureNo).orElseThrow(() ->
                new NotFoundException(PICTURE_NOT_FOUND));

        List<Likes> likes = likesRepository.findByPictureNoOrderByCreatedAtDesc(picture.getNo());

        List<GetPictureLikeUserResponseDto> result = new ArrayList<>();
        likes.forEach( like -> {
            User user = like.getUser();
            result.add(new GetPictureLikeUserResponseDto(user.getNo(), user.getProfileImageUrl(), user.getId(), user.getName()));
        } );
        return result;
    }

    @Override
    @Transactional
    public void patchPictureDescription(User user, Long pictureNo, PatchPictureRequestDto patchPictureRequestDto) {

        Picture picture = pictureRepository.findByNo(pictureNo).orElseThrow(() ->
                new NotFoundException(PICTURE_NOT_FOUND));

        // 접근 권한 확인
        if (!Objects.equals(picture.getFloor().getUser().getNo(), user.getNo())) throw new ForbiddenException(FORBIDDEN_PICTURE);

        // 설명 업데이트
        picture.setDescription(patchPictureRequestDto.getDescription());
        pictureRepository.save(picture);

        // 기존 해시태그 가져오기
        List<PictureHashtag> originalPictureHashtags = pictureHashtagRepository.findAllByPictureNo(picture.getNo());

        // 기존 해시태그 중 없어진 것 삭제
        originalPictureHashtags.forEach(
                originalTag -> {
                    if (!patchPictureRequestDto.getHashtags().contains(originalTag.getHashtag().getHashtag())) {

                        pictureHashtagRepository.deleteByNo(originalTag.getNo());
                        originalTag.getHashtag().setTagCount(originalTag.getHashtag().getTagCount() - 1);
                        hashtagRepository.save(originalTag.getHashtag());

                    }
                }
        );
        // 새로운 해시 태그 추가
        List<String> originalHashtags = new ArrayList<String>();
        originalPictureHashtags.forEach( tag -> {
            originalHashtags.add(tag.getHashtag().getHashtag());
        });

        patchPictureRequestDto.getHashtags().forEach(
                newTag -> {
                    // 새로 추가된 해시태그
                    if (!originalHashtags.contains(newTag)) {
                        Hashtag existingHashtag = hashtagRepository.findByHashtag(newTag);
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
                                    .hashtag(newTag)
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
                    }
                }
        );

    }
}
