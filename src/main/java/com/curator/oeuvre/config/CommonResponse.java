package com.curator.oeuvre.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/** API 응답 공통 형식입니다*/
@AllArgsConstructor
@Getter
@ApiModel(value = "기본 응답")
public class CommonResponse<T> {

    @JsonProperty("timestamp")
    @Schema(description = "응답시간", required = true, example = "2022-07-12T16:20:52.301039")
    private final String timestamp = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);

    @JsonProperty("code")
    @Schema(description = "응답코드", required = true, example = "200")
    private String code;

    @JsonProperty("isSuccess")
    @Schema(description = "성공여부", required = true, example = "true")
    private boolean success;

    @JsonProperty("message")
    @Schema(description = "메시지", required = true, example = "요청에 성공하였습니다.")
    private String message;

    @JsonProperty("result")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Schema(description = "응답 결과", required = false, example = "(응답 결과가 나타납니다.)")
    private T data;

    public static <T> CommonResponse<T> onSuccess(T data) {
        return new CommonResponse<>("200", true, "요청에 성공하였습니다.", data);
    }

    public static <T> CommonResponse<T> onFailure(String code, String message, T data) {
        return new CommonResponse<>(code, false, message, data);
    }
}
