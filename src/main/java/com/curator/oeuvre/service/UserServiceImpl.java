package com.curator.oeuvre.service;

import com.curator.oeuvre.domain.User;
import com.curator.oeuvre.dto.oauth.TokenDto;
import com.curator.oeuvre.dto.oauth.user.request.SignUpRequestDto;
import com.curator.oeuvre.dto.oauth.user.response.SignUpResponseDto;
import com.curator.oeuvre.exception.BaseException;
import com.curator.oeuvre.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.curator.oeuvre.constant.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final JwtService jwtService;

    @Override
    @Transactional
    public SignUpResponseDto signUp(SignUpRequestDto signUpRequestDto) {

        // id 중복 검사
        if (userRepository.findById(signUpRequestDto.getId()).isPresent()) throw new BaseException(DUPLICATED_ID);

        // 이메일 + 타입 중복 검사
        if (userRepository.findByEmailAndType(signUpRequestDto.getEmail(), signUpRequestDto.getType()).isPresent()) {
            throw new BaseException(USER_ALREADY_EXIST);
        }

        // 사용자 생성
        User user = User.builder()
                .email(signUpRequestDto.getEmail())
                .type(signUpRequestDto.getType())
                .id(signUpRequestDto.getId())
                .name(signUpRequestDto.getName())
                .birthday(signUpRequestDto.getBirthday())
                .profileImageUrl(signUpRequestDto.getProfileImageUrl())
                .exhibitionName(signUpRequestDto.getExhibitionName())
                .introduceMessage(signUpRequestDto.getIntroduceMessage())
                .build();
        userRepository.save(user);

        String newAccessToken = jwtService.encodeJwtToken(new TokenDto(user));
        String newRefreshToken = jwtService.encodeJwtRefreshToken(user.getNo());

        // 유저 리프레시 토큰 업데이트
        user.setRefreshToken(newRefreshToken);
        userRepository.save(user);

        return new SignUpResponseDto(user.getNo(), newAccessToken, newRefreshToken);
    }
}

