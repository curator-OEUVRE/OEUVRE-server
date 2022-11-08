package com.curator.oeuvre.service;

import com.curator.oeuvre.domain.Hashtag;
import com.curator.oeuvre.domain.User;
import com.curator.oeuvre.dto.common.response.PageResponseDto;
import com.curator.oeuvre.dto.hashtag.response.GetHashtagPictureDto;
import com.curator.oeuvre.dto.hashtag.response.GetHashtagSearchResponseDto;
import com.curator.oeuvre.dto.hashtag.response.GetPopularHashtagResponseDto;
import com.curator.oeuvre.repository.HashtagRepository;
import com.curator.oeuvre.repository.PictureHashtagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
                    picture.getProfileImageUrl()));
        });
        result.add(new GetPopularHashtagResponseDto(hashtag.getHashtagNo(), hashtag.getHashtag(), pictureResult));
    });
    return result;
    }
}
