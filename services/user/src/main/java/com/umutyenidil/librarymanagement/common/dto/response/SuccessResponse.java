package com.umutyenidil.librarymanagement.common.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class SuccessResponse<T> extends BaseResponse<T> {

    @Builder
    public SuccessResponse(
            String message,
            T data,
            Object metaData
    ) {
        super(true, LocalDateTime.now(), message, data, metaData);
    }

    public static <T> SuccessResponse<T> of(T data) {
        return SuccessResponse.<T>builder()
                .data(data)
                .build();
    }

    public static <T> SuccessResponse<T> of(T data, String message) {
        return SuccessResponse.<T>builder()
                .data(data)
                .message(message)
                .build();
    }

    public static <T> SuccessResponse<T> of(T data, String message, Object metaData) {
        return SuccessResponse.<T>builder()
                .data(data)
                .message(message)
                .metaData(metaData)
                .build();
    }
}
