package com.curator.oeuvre.service;

import com.curator.oeuvre.domain.Hashtag;
import com.curator.oeuvre.domain.User;
import com.curator.oeuvre.dto.common.response.PageResponseDto;
import com.curator.oeuvre.dto.hashtag.response.GetHashtagPictureDto;
import com.curator.oeuvre.dto.hashtag.response.GetHashtagSearchResponseDto;
import com.curator.oeuvre.dto.hashtag.response.GetPopularHashtagResponseDto;
import com.curator.oeuvre.exception.BadRequestException;
import com.curator.oeuvre.exception.NotFoundException;
import com.curator.oeuvre.repository.HashtagRepository;
import com.curator.oeuvre.repository.PictureHashtagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.curator.oeuvre.constant.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class HashtagServiceImpl implements HashtagService {

    private final HashtagRepository hashtagRepository;
    private final PictureHashtagRepository pictureHashtagRepository;

    @Override
    public PageResponseDto<List<GetHashtagSearchResponseDto>> searchHashtags(String keyword, Integer page, Integer size) {

        Pageable pageRequest = PageRequest.of(page, size);

        Page<Hashtag> hashtags = hashtagRepository.findAllByHashtagStartsWithOrderByTagCountDesc(keyword, pageRequest);
        List<GetHashtagSearchResponseDto> result = new ArrayList<>();

        hashtags.forEach( hashtag -> {
            result.add(new GetHashtagSearchResponseDto(hashtag));
        });
        return new PageResponseDto<>(hashtags.isLast(), result);
    }

    @Override
    public List<GetPopularHashtagResponseDto> getPopularHashtags(User user) {

    List<HashtagRepository.GetPopularHashtag> hashtags = hashtagRepository.getPopularHashtags();

    List<GetPopularHashtagResponseDto> result = new ArrayList<>();
    hashtags.forEach( hashtag -> {
        List<PictureHashtagRepository.GetPopularPicture> pictures = pictureHashtagRepository.getPopularPictures(user.getNo(), hashtag.getHashtagNo());

        List<GetHashtagPictureDto> pictureResult = new ArrayList<>();
        pictures.forEach( picture -> {
            pictureResult.add(new GetHashtagPictureDto(
                    picture.getPictureNo(),
                    picture.getImageUrl(),
                    picture.getHeight(),
                    picture.getWidth(),
                    picture.getUserNo(),
                    picture.getId(),
                    picture.getProfileImageUrl(),
                    Objects.equals(picture.getUserNo(), user.getNo())
            ));
        });
        result.add(new GetPopularHashtagResponseDto(hashtag.getHashtagNo(), hashtag.getHashtag(), hashtag.getIsHead(), pictureResult));
    });
    return result;
    }

    @Override
    public PageResponseDto<List<GetHashtagPictureDto>> getHashtagPictures(User user, Long hashtagNo, String sortBy, Integer page, Integer size) {

        Hashtag hashtag = hashtagRepository.findByNoAndStatus(hashtagNo, 1).orElseThrow(() ->
                new NotFoundException(HASHTAG_NOT_FOUND));

        Pageable pageRequest = PageRequest.of(page, size);

        Page<PictureHashtagRepository.GetHashtagPicture> pictures;
        if (Objects.equals(sortBy, "popular")) {
            pictures = pictureHashtagRepository.findAllByHashtagNoSortByPopular(hashtagNo, user.getNo(), pageRequest);
        } else if (Objects.equals(sortBy, "recent")) {
            pictures = pictureHashtagRepository.findAllByHashtagNoSortByRecent(hashtagNo, user.getNo(), pageRequest);
        } else throw new BadRequestException(INVALID_SORT_BY);

        List<GetHashtagPictureDto> result = new ArrayList<>();
        pictures.forEach( picture -> {
            result.add(new GetHashtagPictureDto(
                    picture.getPictureNo(),
                    picture.getImageUrl(),
                    picture.getHeight(),
                    picture.getWidth(),
                    picture.getUserNo(),
                    picture.getId(),
                    picture.getProfileImageUrl(),
                    Objects.equals(picture.getUserNo(), user.getNo())
            ));
        });
        return new PageResponseDto<>(pictures.isLast(), result);
    }
}
