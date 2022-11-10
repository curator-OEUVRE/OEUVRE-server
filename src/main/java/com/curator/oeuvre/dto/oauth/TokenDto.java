package com.curator.oeuvre.dto.oauth;

import com.curator.oeuvre.domain.User;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
public class TokenDto {

    private final Long no;

    public TokenDto(User user) {
        this.no = user.getNo();
    }
}
