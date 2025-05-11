package com.umutyenidil.librarymanagement.common.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ErrorResponse extends BaseResponse<Void> {

    @Builder
    public ErrorResponse(String message, Object metaData) {
        super(false, LocalDateTime.now(), message, null, metaData);
    }

    public static ErrorResponse of(String message) {
        return ErrorResponse.builder()
                .message(message)
                .build();
    }

    public static ErrorResponse of(String message, Object metaData) {
        return ErrorResponse.builder()
                .message(message)
                .metaData(metaData)
                .build();
    }
}