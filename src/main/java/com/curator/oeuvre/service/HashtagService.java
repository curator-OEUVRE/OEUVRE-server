package com.curator.oeuvre.service;

import com.curator.oeuvre.dto.hashtag.response.GetHashtagResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface HashtagService {

    List<GetHashtagResponseDto> searchHashtags(String keyword, Integer page, Integer size);
}
