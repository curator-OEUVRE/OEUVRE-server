package com.curator.oeuvre.service;

import com.curator.oeuvre.domain.Floor;
import com.curator.oeuvre.domain.Picture;
import com.curator.oeuvre.domain.Scrap;
import com.curator.oeuvre.domain.User;
import com.curator.oeuvre.dto.oauth.TokenDto;
import com.curator.oeuvre.dto.picture.response.GetPictureLikeUserResponseDto;
import com.curator.oeuvre.dto.user.request.PatchMyProfileRequestDto;
import com.curator.oeuvre.dto.user.request.SignUpRequestDto;
import com.curator.oeuvre.dto.user.response.*;
import com.curator.oeuvre.exception.BaseException;
import com.curator.oeuvre.exception.NotFoundException;
import com.curator.oeuvre.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.curator.oeuvre.constant.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final FollowingRepository followingRepository;
    private final ScrapRepository scrapRepository;
    private final FloorRepository floorRepository;
    private final PictureRepository pictureRepository;

    @Override
    @Transactional
    public SignUpResponseDto signUp(SignUpRequestDto signUpRequestDto) {

        // id 중복 검사
        if (userRepository.findById(signUpRequestDto.getId()).isPresent()) throw new BaseException(DUPLICATED_ID);

        // 이메일 + 타입 중복 검사
        if (userRepository.findByEmailAndTypeAndStatus(signUpRequestDto.getEmail(), signUpRequestDto.getType(), 1).isPresent()) {
            throw new BaseException(USER_ALREADY_EXIST);
        }

        // 사용자 생성
        User user = User.builder()
                .email(signUpRequestDto.getEmail())
                .type(signUpRequestDto.getType())
                .id(signUpRequestDto.getId())
                .name(signUpRequestDto.getName())
                .birthday(signUpRequestDto.getBirthday())
                .profileImageUrl(signUpRequestDto.getProfileImageUrl())
                .backgroundImageUrl(signUpRequestDto.getBackgroundImageUrl())
                .exhibitionName(signUpRequestDto.getExhibitionName())
                .introduceMessage(signUpRequestDto.getIntroduceMessage())
                .isCommentAlarmOn(signUpRequestDto.getIsAlarmOn())
                .isFollowAlarmOn(signUpRequestDto.getIsAlarmOn())
                .isGroupRequestAlarmOn(signUpRequestDto.getIsAlarmOn())
                .isLikeAlarmOn(signUpRequestDto.getIsAlarmOn())
                .isMessageAlarmOn(signUpRequestDto.getIsAlarmOn())
                .build();
        userRepository.save(user);

        String newAccessToken = jwtService.encodeJwtToken(new TokenDto(user));
        String newRefreshToken = jwtService.encodeJwtRefreshToken(user.getNo());

        // 유저 리프레시 토큰 업데이트
        user.setRefreshToken(newRefreshToken);
        userRepository.save(user);

        return new SignUpResponseDto(user.getNo(), newAccessToken, newRefreshToken);
    }

    @Override
    public CheckIdResponseDto checkId(String id) {
        boolean isPossible;
        isPossible = userRepository.findById(id).isEmpty();

        return new CheckIdResponseDto(isPossible);
    }

    @Override
    public GetMyProfileResponseDto getMyProfile(User user) {

        userRepository.findByNoAndStatus(user.getNo(), 1).orElseThrow(() ->
                new NotFoundException(USER_NOT_FOUND));

        Long followingCount = followingRepository.countFollowingByFollowUserNo(user.getNo());
        Long followerCount = followingRepository.countFollowingByFollowedUserNo(user.getNo());

        return new GetMyProfileResponseDto(user, followingCount, followerCount);
    }

    @Override
    public void patchMyProfile(User user, PatchMyProfileRequestDto patchMyProfileRequestDto) {

        userRepository.findByNoAndStatus(user.getNo(), 1).orElseThrow(() ->
                new NotFoundException(USER_NOT_FOUND));

        user.setName(patchMyProfileRequestDto.getName());
        user.setExhibitionName(patchMyProfileRequestDto.getExhibitionName());
        user.setIntroduceMessage(patchMyProfileRequestDto.getIntroduceMessage());
        user.setProfileImageUrl(patchMyProfileRequestDto.getProfileImageUrl());
        user.setBackgroundImageUrl(patchMyProfileRequestDto.getBackgroundImageUrl());
        userRepository.save(user);

    }

    @Override
    public List<GetMyFloorResponseDto> getMyFloors(User user, Integer page, Integer size) {

        userRepository.findByNoAndStatus(user.getNo(), 1).orElseThrow(() ->
                new NotFoundException(USER_NOT_FOUND));

        Pageable pageRequest = PageRequest.of(page, size);
        Page<Floor> floors = floorRepository.findAllByUserNoAndStatusAndIsGroupExhibitionOrderByQueueDesc(user.getNo(), 1, false, pageRequest);

        List<GetMyFloorResponseDto> result = new ArrayList<>();
        floors.forEach( floor -> {
            List<String> imageUrls = pictureRepository.findTop7ByFloorNoAndStatusOrderByQueue(floor.getNo(), 1).stream().map(Picture::getImageUrl).collect(Collectors.toList());
            result.add(new GetMyFloorResponseDto(floor, imageUrls));
        });
        return result;
    }

    @Override
    public List<GetMyCollectionResponseDto> getMyCollection(User user, Integer page, Integer size) {

        userRepository.findByNoAndStatus(user.getNo(), 1).orElseThrow(() ->
                new NotFoundException(USER_NOT_FOUND));

        Pageable pageRequest = PageRequest.of(page, size);
        Page<Scrap> collection = scrapRepository.findAllByUserNoAndPicture_statusOrderByCreatedAtDesc(user.getNo(), 1, pageRequest);

        List<GetMyCollectionResponseDto> result = new ArrayList<>();
        collection.forEach( scrap -> {
            result.add(new GetMyCollectionResponseDto(scrap.getPicture()));
        });
        return result;
    }
}

