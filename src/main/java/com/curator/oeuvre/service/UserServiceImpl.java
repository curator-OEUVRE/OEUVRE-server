package com.curator.oeuvre.service;

import com.curator.oeuvre.domain.*;
import com.curator.oeuvre.dto.oauth.TokenDto;
import com.curator.oeuvre.dto.user.request.PatchMyProfileRequestDto;
import com.curator.oeuvre.dto.user.request.SignUpRequestDto;
import com.curator.oeuvre.dto.user.response.*;
import com.curator.oeuvre.exception.BadRequestException;
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
import java.util.Objects;
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
    public List<GetUserFloorResponseDto> getUserFloors(User me, Long userNo, Integer page, Integer size) {

        userRepository.findByNoAndStatus(userNo, 1).orElseThrow(() ->
                new NotFoundException(USER_NOT_FOUND));

        boolean isMine = Objects.equals(me.getNo(), userNo);

        Pageable pageRequest = PageRequest.of(page, size);
        Page<Floor> floors;
        if (isMine) floors = floorRepository.findAllByUserNoAndStatusAndIsGroupExhibitionOrderByQueueDesc(userNo, 1, false, pageRequest);
        else floors = floorRepository.findAllByUserNoAndStatusAndIsPublicAndIsGroupExhibitionOrderByQueueDesc(userNo, 1, true, false, pageRequest);

        List<GetUserFloorResponseDto> result = new ArrayList<>();
        floors.forEach( floor -> {
            List<String> imageUrls = pictureRepository.findTop7ByFloorNoAndStatusOrderByQueue(floor.getNo(), 1).stream().map(Picture::getImageUrl).collect(Collectors.toList());
            result.add(new GetUserFloorResponseDto(floor, imageUrls));
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

    @Override
    public GetUserProfileResponseDto getUserProfile(User me, Long userNo) {

        User user = userRepository.findByNoAndStatus(userNo, 1).orElseThrow(() ->
                new NotFoundException(USER_NOT_FOUND));

        Long followingCount = followingRepository.countFollowingByFollowUserNo(userNo);
        Long followerCount = followingRepository.countFollowingByFollowedUserNo(userNo);

        Boolean isFollower = followingRepository.existsByFollowUserNoAndFollowedUserNoAndStatus(userNo, me.getNo(), 1);
        Boolean isFollowing = followingRepository.existsByFollowUserNoAndFollowedUserNoAndStatus(me.getNo(), userNo, 1);

        return new GetUserProfileResponseDto(user, followingCount, followerCount, isFollower, isFollowing);
    }

    @Override
    public void postFollow(User me, Long userNo) {

        User user = userRepository.findByNoAndStatus(userNo, 1).orElseThrow(() ->
                new NotFoundException(USER_NOT_FOUND));

        if (followingRepository.existsByFollowUserNoAndFollowedUserNoAndStatus(me.getNo(), userNo, 1))
            throw new BadRequestException(ALREADY_FOLLOWED);

        Following following = Following.builder()
                .followUser(me)
                .followedUser(user)
                .build();
        followingRepository.save(following);
    }

    @Override
    @Transactional
    public void deleteFollow(User me, Long userNo) {

        User user = userRepository.findByNoAndStatus(userNo, 1).orElseThrow(() ->
                new NotFoundException(USER_NOT_FOUND));

        if (!followingRepository.existsByFollowUserNoAndFollowedUserNoAndStatus(me.getNo(), userNo, 1))
            throw new BadRequestException(FOLLOW_NOTE_FOUND);

        followingRepository.deleteByFollowUserNoAndFollowedUserNoAndStatus(me.getNo(), userNo, 1);
    }

    @Override
    public List<GetUserFollowingResponseDto> getUserFollowings(User me, Long userNo) {

        userRepository.findByNoAndStatus(userNo, 1).orElseThrow(() ->
                new NotFoundException(USER_NOT_FOUND));

        List<User> followingUsers = followingRepository.findAllByFollowUserNoAndStatus(userNo, 1).stream().map(Following::getFollowedUser).collect(Collectors.toList());

        List<GetUserFollowingResponseDto> result = new ArrayList<>();
        followingUsers.forEach( followingUser -> {
            boolean isFollowing = followingRepository.existsByFollowUserNoAndFollowedUserNoAndStatus(me.getNo(), followingUser.getNo(), 1);
            result.add(new GetUserFollowingResponseDto(followingUser, isFollowing));
        });
        return result;
    }

    @Override
    public List<GetUserFollowerResponseDto> getUserFollowers(User me, Long userNo) {

        userRepository.findByNoAndStatus(userNo, 1).orElseThrow(() ->
                new NotFoundException(USER_NOT_FOUND));

        List<User> followers = followingRepository.findAllByFollowedUserNoAndStatus(userNo, 1).stream().map(Following::getFollowUser).collect(Collectors.toList());

        List<GetUserFollowerResponseDto> result = new ArrayList<>();
        followers.forEach( follower -> {
            boolean isFollowing = followingRepository.existsByFollowUserNoAndFollowedUserNoAndStatus(me.getNo(), follower.getNo(), 1);
            result.add(new GetUserFollowerResponseDto(follower, isFollowing));
        });
        return result;
    }
}

