package com.curator.oeuvre.service;

import com.curator.oeuvre.dto.oauth.user.request.SignUpRequestDto;
import com.curator.oeuvre.dto.oauth.user.response.SignUpResponseDto;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    SignUpResponseDto signUp(SignUpRequestDto signUpRequestDto);

}
