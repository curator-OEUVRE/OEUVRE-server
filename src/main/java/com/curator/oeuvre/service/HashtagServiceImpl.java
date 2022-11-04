package com.curator.oeuvre.service;

import com.curator.oeuvre.domain.Hashtag;
import com.curator.oeuvre.dto.common.response.PageResponseDto;
import com.curator.oeuvre.dto.hashtag.response.GetHashtagResponseDto;
import com.curator.oeuvre.repository.HashtagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HashtagServiceImpl implements HashtagService {

    private final HashtagRepository hashtagRepository;

    @Override
    public PageResponseDto<List<GetHashtagResponseDto>> searchHashtags(String keyword, Integer page, Integer size) {

        Pageable pageRequest = PageRequest.of(page, size);

        Page<Hashtag> hashtags = hashtagRepository.findAllByHashtagStartsWithOrderByTagCountDesc(keyword, pageRequest);
        List<GetHashtagResponseDto> result = new ArrayList<>();

        hashtags.forEach( hashtag -> {
            result.add(new GetHashtagResponseDto(hashtag));
        });
        return new PageResponseDto<>(hashtags.isLast(), result);
    }
}
