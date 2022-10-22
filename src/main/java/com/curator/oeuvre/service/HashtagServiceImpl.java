package com.curator.oeuvre.service;

import com.curator.oeuvre.dto.hashtag.response.GetHashtagResponseDto;
import com.curator.oeuvre.repository.HashtagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HashtagServiceImpl implements HashtagService {

    private final HashtagRepository hashtagRepository;

    @Override
    public List<GetHashtagResponseDto> searchHashtags(String keyword, Integer page, Integer size) {
        Pageable pageRequest = PageRequest.of(page, size);
        return hashtagRepository.findAllByHashtagStartsWithOrderByTagCountDesc(keyword, pageRequest)
                .stream().map(GetHashtagResponseDto::new).collect(Collectors.toList());
    }
}
