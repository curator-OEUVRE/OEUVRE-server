package com.curator.oeuvre.dto.picture.response;

import com.curator.oeuvre.domain.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@ApiModel(value = "π μ¬μ§ μ’μμ ν μ μ  μ μ²΄ μ‘°ν API Response")
public class GetPictureLikeUserResponseDto {

    @ApiModelProperty(notes = "νμ no", example = "1")
    private final Long userNo;

    @ApiModelProperty(notes = "νλ‘ν μ΄λ―Έμ§ url", example = "image_url")
    private final String profileImageUrl;

    @ApiModelProperty(notes = "μμ΄λ", example = "one_zzini_")
    private final String id;

    @ApiModelProperty(notes = "μ΄λ¦", example = "κΉμμ§")
    private final String name;


    public GetPictureLikeUserResponseDto (
            User user
    ) {
        this.userNo = user.getNo();
        this.profileImageUrl = user.getProfileImageUrl();
        this.id = user.getId();
        this.name = user.getName();
    }
}

