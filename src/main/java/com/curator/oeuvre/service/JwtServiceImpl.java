package com.curator.oeuvre.service;

import com.curator.oeuvre.dto.oauth.TokenDto;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Base64;
import java.util.Date;

@Component
@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {

    private final CustomUserDetailServiceImpl customUserDetailsService;

    @Value("${jwt.secret}")
    private String JWT_SECRET;

    @Override
    public String encodeJwtToken(TokenDto tokenDto) {
        Date now = new Date();

        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setIssuer("oeuvre")
                .setIssuedAt(now)
                .setSubject(tokenDto.getNo().toString())
                .setExpiration(new Date(now.getTime() + Duration.ofDays(180).toMillis()))
                .claim("no", tokenDto.getNo())
                .claim("roles", "USER")
                .signWith(SignatureAlgorithm.HS256,
                        Base64.getEncoder().encodeToString(("" + JWT_SECRET).getBytes(
                                StandardCharsets.UTF_8)))
                .compact();
    }

    @Override
    public String encodeJwtRefreshToken(Long no) {
        Date now = new Date();
        return Jwts.builder()
                .setIssuedAt(now)
                .setSubject(no.toString())
                .setExpiration(new Date(now.getTime() + Duration.ofMinutes(20160).toMillis()))
                .claim("no", no)
                .claim("roles", "USER")
                .signWith(SignatureAlgorithm.HS256,
                        Base64.getEncoder().encodeToString(("" + JWT_SECRET).getBytes(
                                StandardCharsets.UTF_8)))
                .compact();
    }

    @Override
    public Long getUserNoFromJwtToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(Base64.getEncoder().encodeToString(("" + JWT_SECRET).getBytes(
                        StandardCharsets.UTF_8)))
                .parseClaimsJws(token)
                .getBody();
        return Long.parseLong(claims.getSubject());
    }

    @Override
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(
                this.getUserNoFromJwtToken(token).toString());
        return new UsernamePasswordAuthenticationToken(userDetails, "",
                userDetails.getAuthorities());
    }


    @Override
    public Boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey(Base64.getEncoder().encodeToString(("" + JWT_SECRET).getBytes(
                            StandardCharsets.UTF_8))).parseClaimsJws(token);

            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public String getToken(HttpServletRequest request) {
        return request.getHeader("X-AUTH-TOKEN");
    }
}
