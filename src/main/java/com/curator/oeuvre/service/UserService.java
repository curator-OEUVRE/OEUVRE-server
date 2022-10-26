package com.curator.oeuvre.service;

import com.curator.oeuvre.domain.User;
import com.curator.oeuvre.dto.user.request.PatchMyProfileRequestDto;
import com.curator.oeuvre.dto.user.request.SignUpRequestDto;
import com.curator.oeuvre.dto.user.response.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public interface UserService {

    SignUpResponseDto signUp(SignUpRequestDto signUpRequestDto);

    CheckIdResponseDto checkId(String id);

    GetMyProfileResponseDto getMyProfile(User user);

    void patchMyProfile(User user, PatchMyProfileRequestDto patchMyProfileRequestDto);

    List<GetMyFloorResponseDto> getMyFloors(User user, Integer page, Integer size);

    List<GetMyCollectionResponseDto> getMyCollection(User user, Integer page, Integer size);
}
