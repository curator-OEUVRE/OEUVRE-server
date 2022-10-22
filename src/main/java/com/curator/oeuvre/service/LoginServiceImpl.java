package com.curator.oeuvre.service;

import static com.curator.oeuvre.constant.ErrorCode.*;
import com.curator.oeuvre.domain.User;
import com.curator.oeuvre.dto.oauth.TokenDto;
import com.curator.oeuvre.dto.oauth.request.LoginRequestDto;
import com.curator.oeuvre.dto.oauth.response.LoginResponseDto;
import com.curator.oeuvre.exception.BadRequestException;
import com.curator.oeuvre.exception.BaseException;
import com.curator.oeuvre.exception.ForbiddenException;
import com.curator.oeuvre.repository.UserRepository;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.gson.*;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.RSAPublicKeySpec;
import java.util.*;

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
    public LoginResponseDto googleLogin(LoginRequestDto loginRequestDto) {

        String email;
        String GOOGLE_WEB_CLIENT_ID = "760182900541-vtqm5r7hn878rigsq8vnpo4tu9ff7r2u.apps.googleusercontent.com";

        // 1. 토큰 검증
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new GsonFactory())
                .setAudience(Collections.singletonList(GOOGLE_WEB_CLIENT_ID))
                .build();

        try {
            GoogleIdToken idToken = verifier.verify(loginRequestDto.getToken());
            GoogleIdToken.Payload payload = idToken.getPayload();
            email = payload.getEmail();
        } catch (Exception e) {
            throw new BadRequestException(GOOGLE_BAD_REQUEST);
        }

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

    public PublicKey getPublicKey(JsonObject object) {
        String nStr = object.get("n").toString();
        String eStr = object.get("e").toString();

        byte[] nBytes = Base64.getUrlDecoder().decode(nStr.substring(1, nStr.length() - 1));
        byte[] eBytes = Base64.getUrlDecoder().decode(eStr.substring(1, eStr.length() - 1));

        BigInteger n = new BigInteger(1, nBytes);
        BigInteger e = new BigInteger(1, eBytes);

        try {
            RSAPublicKeySpec publicKeySpec = new RSAPublicKeySpec(n, e);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return keyFactory.generatePublic(publicKeySpec);
        } catch (Exception exception) {
            throw new BadRequestException(FAIL_TO_MAKE_APPLE_PUBLIC_KEY);
        }
    }

    @Override
    public LoginResponseDto appleLogin(LoginRequestDto loginRequestDto) {

        String token = loginRequestDto.getToken();
        String appleReqUrl = "https://appleid.apple.com/auth/keys";
        StringBuffer result = new StringBuffer();
        String email;

        // 애플 api로 토큰 검증용 공개키 요청
        try {
            URL url = new URL(appleReqUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            System.out.println(token);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("charset", "utf-8");

            int responseCode = conn.getResponseCode();
            System.out.println(responseCode);
            if (responseCode != HttpStatus.OK.value()) throw new ForbiddenException(APPLE_SERVER_ERROR);

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String line = "";

            while ((line = br.readLine()) != null) {
                result.append(line);
            }

        } catch (Exception e) {
            throw new ForbiddenException(APPLE_SERVER_ERROR);
        }

        //Gson 라이브러리로 JSON파싱
        // 통신 결과에서 공개키 목록 가져오기
        JsonParser parser = new JsonParser();
        JsonObject keys = (JsonObject) parser.parse(result.toString());
        JsonArray publicKeys = (JsonArray) keys.get("keys");

        try {
            // 클라이언트로부터 입력받은 jwt(idToken)에서 공개키와 비교할 항목 추출하기
            String headerOfIdentityToken = token.substring(0, token.indexOf("."));
            String header = new String(Base64.getDecoder().decode(headerOfIdentityToken), "UTF-8");

            JsonObject parsedHeader = (new Gson()).fromJson(header, JsonObject.class);
            JsonElement kid = parsedHeader.get("kid");
            JsonElement alg = parsedHeader.get("alg");

            // 애플의 공개키 3개중 클라이언트 토큰과 kid, alg 일치하는 것 찾기
            JsonObject avaliableObject = null;
            for (int i = 0; i < publicKeys.size(); i++) {
                JsonObject appleObject = (JsonObject) publicKeys.get(i);
                JsonElement appleKid = appleObject.get("kid");
                JsonElement appleAlg = appleObject.get("alg");

                if (Objects.equals(appleKid, kid) && Objects.equals(appleAlg, alg)) {
                    avaliableObject = appleObject;
                    break;
                }
            }

            //일치하는 공개키 없음
            if (ObjectUtils.isEmpty(avaliableObject))
                throw new BadRequestException(APPLE_BAD_REQUEST);

            PublicKey publicKey = this.getPublicKey(avaliableObject);

            // 일치하는 키를 이용해 정보 확인 후, 사용자 정보 가져오기
            Claims userInfo = Jwts.parser().setSigningKey(publicKey).parseClaimsJws(token).getBody();
            JsonObject userInfoObject = (JsonObject) parser.parse(new Gson().toJson(userInfo));

            if (!Objects.equals(userInfoObject.get("iss").getAsString(), "https://appleid.apple.com"))
                throw new BadRequestException(APPLE_BAD_REQUEST);

            if (!Objects.equals(userInfoObject.get("aud").getAsString(), "com.curator.oeuvre"))
                throw new BadRequestException(APPLE_BAD_REQUEST);

            email = userInfoObject.get("email").getAsString();

        } catch (Exception e) {
            throw new BadRequestException(APPLE_BAD_REQUEST);
        }

        // 이메일, 타입으로 유저 조회
        // 가입되지 않은 유저 일 경우 에러와 함께 이메일 반환
        User user = userRepository.findByEmailAndType(email, "APPLE").orElseThrow(() ->
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

        return new LoginResponseDto(newAccessToken, newRefreshToken);
    }
}
