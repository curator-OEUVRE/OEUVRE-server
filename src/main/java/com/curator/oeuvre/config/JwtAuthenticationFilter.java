package com.curator.oeuvre.config;

import static com.curator.oeuvre.constant.ErrorCode.*;
import com.curator.oeuvre.exception.BaseException;
import com.curator.oeuvre.exception.UnauthorizedException;
import com.curator.oeuvre.service.JwtServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {

    private final JwtServiceImpl jwtTokenService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {
        String token = jwtTokenService.getToken((HttpServletRequest) request);
        if (token != null) {
            if (jwtTokenService.validateToken(token)) {
                Authentication authentication = jwtTokenService.getAuthentication(token);
                System.out.println("authentication = " + authentication);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                throw new UnauthorizedException("유효하지 않은 토큰입니다.");
            }
        }
        chain.doFilter(request, response);
    }

}
