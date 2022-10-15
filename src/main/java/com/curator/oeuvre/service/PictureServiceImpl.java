package com.curator.oeuvre.service;

import com.curator.oeuvre.domain.Picture;
import com.curator.oeuvre.domain.User;
import com.curator.oeuvre.dto.picture.response.GetPictureResponseDto;
import com.curator.oeuvre.exception.NotFoundException;
import com.curator.oeuvre.repository.LikesRepository;
import com.curator.oeuvre.repository.PictureRepository;
import com.curator.oeuvre.repository.ScrapRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Objects;
import static com.curator.oeuvre.constant.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class PictureServiceImpl implements PictureService{

    private final PictureRepository pictureRepository;
    private final LikesRepository likesRepository;
    private final ScrapRepository scrapRepository;

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
}
