package com.curator.oeuvre.dto.oauth.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.validation.annotation.Validated;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Validated
@ApiModel(value = "π λ‘κ·ΈμΈ API Request")

public class LoginRequestDto {

    @ApiModelProperty(notes = "μμ ν ν°", example = "p1b3M_ikmtHivvFJqwY5bXAYg-ilCq4E7DnJwlT5CisM0wAAAYOPy1oR")
    @NotNull
    private String token;
}
