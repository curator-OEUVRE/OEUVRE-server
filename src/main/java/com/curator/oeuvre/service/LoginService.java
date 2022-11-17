package com.curator.oeuvre.service;

import com.curator.oeuvre.domain.User;
import com.curator.oeuvre.dto.oauth.request.LoginRequestDto;
import com.curator.oeuvre.dto.oauth.response.LoginResponseDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Service;

@Service
public interface LoginService {

    LoginResponseDto kakaoLogin(LoginRequestDto loginRequestDto);

    LoginResponseDto googleLogin(LoginRequestDto loginRequestDto) throws JsonProcessingException;

    LoginResponseDto appleLogin(LoginRequestDto loginRequestDto);

    LoginResponseDto updateUserToken(User user);

    Boolean getIsGuestLoginAvailable();

    LoginResponseDto guestLogin();
}
