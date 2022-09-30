package com.curator.oeuvre.service;

import com.curator.oeuvre.dto.oauth.user.request.SignUpRequestDto;
import com.curator.oeuvre.dto.oauth.user.response.SignUpResponseDto;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    SignUpResponseDto signUp(SignUpRequestDto signUpRequestDto);
}
