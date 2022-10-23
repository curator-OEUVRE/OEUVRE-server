package com.curator.oeuvre.service;

import com.curator.oeuvre.domain.User;
import com.curator.oeuvre.dto.user.request.SignUpRequestDto;
import com.curator.oeuvre.dto.user.response.GetMyProfileResponseDto;
import com.curator.oeuvre.dto.user.response.SignUpResponseDto;
import com.curator.oeuvre.dto.user.response.CheckIdResponseDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public interface UserService {

    SignUpResponseDto signUp(SignUpRequestDto signUpRequestDto);

    CheckIdResponseDto checkId(String id);

    GetMyProfileResponseDto getMyProfile(User user);
}
