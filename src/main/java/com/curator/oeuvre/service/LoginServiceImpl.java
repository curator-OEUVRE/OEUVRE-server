package com.curator.oeuvre.service;

import static com.curator.oeuvre.constant.ErrorCode.*;
import com.curator.oeuvre.domain.User;
import com.curator.oeuvre.dto.oauth.TokenDto;
import com.curator.oeuvre.dto.oauth.request.LoginRequestDto;
import com.curator.oeuvre.dto.oauth.response.LoginResponseDto;
import com.curator.oeuvre.exception.BadRequestException;
import com.curator.oeuvre.exception.BaseException;
import com.curator.oeuvre.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final UserRepository userRepository;
    private final JwtService jwtService;

    @Override
    public LoginResponseDto kakaoLogin(LoginRequestDto loginRequestDto) {
        String token = loginRequestDto.getToken();
        String kakaoReqURL = "https://kapi.kakao.com/v2/user/me";
        try {
            URL url = new URL(kakaoReqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            System.out.println(token);
            // 1. POST 요청을 위해 기본값이 false인 DoOutput을 true로
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestProperty("Authorization", "Bearer " + token); //전송할 header 작성, access_token전송

            // 200 성공 아닐 시 에러
            int responseCode = conn.getResponseCode();
            System.out.println("errorStream = " + conn.getErrorStream());
            System.out.println("conn.getResponseMessage() = " + conn.getResponseMessage());
            System.out.println(responseCode);
            if (responseCode != HttpStatus.OK.value()) throw new BadRequestException(KAKAO_BAD_REQUEST);

            //요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기 (응답 한번에 읽어서 string으로 리턴)
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }

            //Gson 라이브러리로 JSON파싱
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);

            // 카카오 토큰 정보 가져오기
            Long id = element.getAsJsonObject().get("id").getAsLong();
            boolean hasEmail = element.getAsJsonObject().get("kakao_account").getAsJsonObject().get("has_email").getAsBoolean();
            String email = "";
            if(hasEmail){
                email = element.getAsJsonObject().get("kakao_account").getAsJsonObject().get("email").getAsString();
            } else throw new BadRequestException(KAKAO_USER_EMAIL_NOT_FOUND);

            //System.out.println(email);
            br.close();
            String finalEmail = email;

            // 이메일, 타입으로 유저 조회
            // 가입되지 않은 유저 일 경우 에러와 함께 이메일 반환
            User user = userRepository.findByEmailAndType(email, "KAKAO").orElseThrow(() ->
                    new BaseException(USER_NOT_FOUND, Map.of("email", finalEmail)));

            // 가입된 유저 확인 시 jwt, refreshToken 반환
            String newAccessToken = jwtService.encodeJwtToken(new TokenDto(user));
            String newRefreshToken = jwtService.encodeJwtRefreshToken(user.getNo());

            // 유저 리프레시 토큰 업데이트
            user.setRefreshToken(newRefreshToken);
            userRepository.save(user);

            return new LoginResponseDto(newAccessToken, newRefreshToken);

        } catch (IOException e) {
            throw new BaseException(KAKAO_USER_NOT_FOUND);
        }
    }

    @Override
    public LoginResponseDto googleLogin(LoginRequestDto loginRequestDto) throws JsonProcessingException {

        String token = loginRequestDto.getToken();
        String googleReqURL="https://www.googleapis.com/oauth2/v1/userinfo";
        RestTemplate restTemplate = new RestTemplate();
        ObjectMapper objectMapper = new ObjectMapper();

        //header에 accessToken을 담는다.
        org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
        headers.add("Authorization","Bearer "+ token);

        //HttpEntity를 하나 생성해 헤더를 담아서 restTemplate으로 구글과 통신하게 된다.

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity(headers);
        ResponseEntity<String> response = restTemplate.exchange(googleReqURL, HttpMethod.GET, request, String.class);

        JsonParser parser = new JsonParser();
        JsonElement element = parser.parse(response.getBody());
        String email = element.getAsJsonObject().get("email").getAsString();

        // 이메일, 타입으로 유저 조회
        // 가입되지 않은 유저 일 경우 에러와 함께 이메일 반환
        User user = userRepository.findByEmailAndType(email, "GOOGLE").orElseThrow(() ->
                new BaseException(USER_NOT_FOUND, Map.of("email", email)));

        // 가입된 유저 확인 시 jwt, refreshToken 반환
        String newAccessToken = jwtService.encodeJwtToken(new TokenDto(user));
        String newRefreshToken = jwtService.encodeJwtRefreshToken(user.getNo());

        // 유저 리프레시 토큰 업데이트
        user.setRefreshToken(newRefreshToken);
        userRepository.save(user);

        return new LoginResponseDto(newAccessToken, newRefreshToken);
    }

    @Override
    public LoginResponseDto updateUserToken(User user) {
        String newRefreshToken = jwtService.encodeJwtRefreshToken(user.getNo());
        String newAccessToken = jwtService.encodeJwtToken(new TokenDto(user));

        user.setRefreshToken(newRefreshToken);
        userRepository.save(user);

        LoginResponseDto loginDto  = new LoginResponseDto(newAccessToken, newRefreshToken);

        return loginDto;
    }
}
