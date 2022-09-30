package com.curator.oeuvre.service;

import com.curator.oeuvre.dto.oauth.TokenDto;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@Service
public interface JwtService {

    String encodeJwtToken(TokenDto tokenDto);
    String encodeJwtRefreshToken(Long no);
}
