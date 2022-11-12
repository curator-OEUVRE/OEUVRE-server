package com.curator.oeuvre.service;

import com.curator.oeuvre.domain.User;
import com.curator.oeuvre.dto.common.response.PageResponseDto;
import com.curator.oeuvre.dto.hashtag.response.GetHashtagPictureDto;
import com.curator.oeuvre.dto.hashtag.response.GetHashtagSearchResponseDto;
import com.curator.oeuvre.dto.hashtag.response.GetPopularHashtagResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface HashtagService {

    PageResponseDto<List<GetHashtagSearchResponseDto>> searchHashtags(String keyword, Integer page, Integer size);

    List<GetPopularHashtagResponseDto> getPopularHashtags(User user);

    PageResponseDto<List<GetHashtagPictureDto>> getHashtagPictures(User user, Long hashtagNo, String sortBy, Integer page, Integer size);
}
