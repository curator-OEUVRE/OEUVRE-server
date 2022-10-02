package com.curator.oeuvre.service;

import com.curator.oeuvre.dto.oauth.TokenDto;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Component
@Service
public interface JwtService {

    String encodeJwtToken(TokenDto tokenDto);

    String encodeJwtRefreshToken(Long no);

    Long getUserNoFromJwtToken(String token);

    Authentication getAuthentication(String token);

    Boolean validateToken(String token);

    String getToken(HttpServletRequest request);
}
