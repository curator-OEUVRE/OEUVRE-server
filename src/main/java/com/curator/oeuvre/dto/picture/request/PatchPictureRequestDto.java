package com.curator.oeuvre.dto.picture.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Validated
@ApiModel(value = "๐ ์ฌ์ง ์ค๋ช ์์  API Request")

public class PatchPictureRequestDto {

    @Length(max = 50)
    @ApiModelProperty(notes = "์ฌ์ง ์ค๋ช", example = "๋ธ์์ ๋ณด๋ฉด์ ํ์ปท")
    private String description;

    @ApiModelProperty(notes = "ํด์ํ๊ทธ ๋ชฉ๋ก", example = "[\"#๋ธ์\", \"#๋ฐ๋ค\", \"#์ ์ฃผ\"]")
    private List<String> hashtags;
}
