package com.curator.oeuvre.service;

import com.curator.oeuvre.domain.User;
import com.curator.oeuvre.dto.oauth.request.LoginRequestDto;
import com.curator.oeuvre.dto.oauth.response.LoginResponseDto;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public interface LoginService {

    LoginResponseDto kakaoLogin(LoginRequestDto loginRequestDto);

    LoginResponseDto googleLogin(LoginRequestDto loginRequestDto) throws IOException;

    LoginResponseDto updateUserToken(User user);

}
