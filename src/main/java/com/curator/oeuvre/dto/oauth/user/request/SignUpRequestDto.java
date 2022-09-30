package com.curator.oeuvre.dto.oauth.user.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.lang.Nullable;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignUpRequestDto {

    @ApiModelProperty(example = "sobaek2000@naver.com")
    @Email(message = "email 형식을 확인해주세요.")
    @NotNull(message = "email을 입력해주세요.")
    private String email;

    @ApiModelProperty(example = "KAKAO")
    @NotNull(message = "type을 입력해주세요.")
    private String type;

    @ApiModelProperty(example = "one_zzini_")
    @Length(min = 1, max = 15, message = "id 길이를 2에서 15사이로 입력해주세요.")
    @NotNull(message = "id를 입력해주세요.")
    private String id;

    @ApiModelProperty(example = "김원진")
    @Length(min = 2, max = 12, message = "name 길이를 2에서 12사이로 입력해주세요.")
    @NotNull(message = "name을 입력해주세요.")
    private String name;

    @ApiModelProperty(example = "2000-06-21")
    @Length(min = 10, max = 10, message = "birthday 길이를 10으로 입력해주세요.")
    @NotNull(message = "birthday를 입력해주세요.")
    private String birthday;

    @ApiModelProperty(example = "image_url")
    @Nullable
    private String profileImageUrl;

    @ApiModelProperty(example = "원진이의 전시회")
    @Length(min = 4, max = 15, message = "exhibitionName 길이를 4에서 15사이로 입력해주세요.")
    @NotNull(message = "exhibitionName을 입력해주세요.")
    private String exhibitionName;

    @ApiModelProperty(example = "안녕하세요 사진찍기를 좋아하는 김원진입니덩")
    @Length(max = 50, message = "IntroduceMessage 길이를 50안으로 입력해주세요.")
    @Nullable
    private String introduceMessage;
}
