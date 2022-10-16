package com.curator.oeuvre.service;

import com.curator.oeuvre.dto.hashtag.response.GetHashtagResponseDto;
import com.curator.oeuvre.repository.HashtagRepository;
import com.curator.oeuvre.repository.PictureHashtagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor

public class HashtagServiceImpl implements HashtagService {

    private final HashtagRepository hashtagRepository;
    private final PictureHashtagRepository pictureHashtagRepository;

    @Override
    public List<GetHashtagResponseDto> searchHashtags(String keyword, Integer page, Integer size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return null;
    }
}
