package com.nexerp.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
    private boolean success;
    private String  message;
    private T       data;
    private List<String> errors;
    @Builder.Default
    private long timestamp = Instant.now().toEpochMilli();

    public static <T> ApiResponse<T> ok(T data) {
        return ApiResponse.<T>builder().success(true).data(data).build();
    }
    public static <T> ApiResponse<T> ok(T data, String msg) {
        return ApiResponse.<T>builder().success(true).data(data).message(msg).build();
    }
    public static <T> ApiResponse<T> fail(String msg) {
        return ApiResponse.<T>builder().success(false).message(msg).build();
    }
    public static <T> ApiResponse<T> fail(String msg, List<String> errors) {
        return ApiResponse.<T>builder().success(false).message(msg).errors(errors).build();
    }
}
