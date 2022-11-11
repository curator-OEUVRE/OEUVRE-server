package com.curator.oeuvre.service;

import com.curator.oeuvre.domain.User;
import com.curator.oeuvre.dto.common.response.PageResponseDto;
import com.curator.oeuvre.dto.user.request.PatchMyProfileRequestDto;
import com.curator.oeuvre.dto.user.request.SignUpRequestDto;
import com.curator.oeuvre.dto.user.response.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    SignUpResponseDto signUp(SignUpRequestDto signUpRequestDto);

    CheckIdResponseDto checkId(String id);

    GetMyProfileResponseDto getMyProfile(User user);

    void patchMyProfile(User user, PatchMyProfileRequestDto patchMyProfileRequestDto);

    PageResponseDto<List<GetUserFloorResponseDto>> getUserFloors(User me, Long userNo, Integer page, Integer size);

    PageResponseDto<List<GetMyCollectionResponseDto>> getMyCollection(User user, Integer page, Integer size);

    GetUserProfileResponseDto getUserProfile(User me, Long userNo);

    void postFollow(User me, Long userNo);

    void deleteFollow(User me, Long userNo);

    List<GetUserFollowingResponseDto> getUserFollowings(User me, Long userNo);

    List<GetUserFollowerResponseDto> getUserFollowers(User me, Long userNo);

    PageResponseDto<List<GetUserSearchResponseDto>> searchUsers(String keyword, Integer page, Integer size);

    void deleteUser(User user);

}
