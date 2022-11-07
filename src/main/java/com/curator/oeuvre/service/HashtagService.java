package com.curator.oeuvre.service;

import com.curator.oeuvre.domain.User;
import com.curator.oeuvre.dto.common.response.PageResponseDto;
import com.curator.oeuvre.dto.hashtag.response.GetHashtagResponseDto;
import com.curator.oeuvre.dto.hashtag.response.GetPopularHashtagResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface HashtagService {

    PageResponseDto<List<GetHashtagResponseDto>> searchHashtags(String keyword, Integer page, Integer size);

    List<GetPopularHashtagResponseDto> getPopularHashtags(User user);
}
