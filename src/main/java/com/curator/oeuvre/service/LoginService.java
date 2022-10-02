package com.curator.oeuvre.service;

import com.curator.oeuvre.domain.User;
import com.curator.oeuvre.dto.oauth.request.LoginRequestDto;
import com.curator.oeuvre.dto.oauth.response.LoginResponseDto;
import org.springframework.stereotype.Service;

@Service
public interface LoginService {

    LoginResponseDto kakoLogin(LoginRequestDto loginRequestDto);

    LoginResponseDto updateUserToken(User user);

}
