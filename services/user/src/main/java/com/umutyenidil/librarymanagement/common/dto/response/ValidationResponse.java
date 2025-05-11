package com.umutyenidil.librarymanagement.common.dto.response;

import lombok.*;
import java.time.LocalDateTime;
import java.util.Map;

@Getter
public class ValidationResponse extends BaseResponse<Void> {

    @Builder
    public ValidationResponse(String message, Map<String, String> validationErrors) {
        super(false, LocalDateTime.now(), message, null, validationErrors);
    }

    public static ValidationResponse of(Map<String, String> fieldErrors) {
        return ValidationResponse.builder()
                .validationErrors(fieldErrors)
                .build();
    }

    public static ValidationResponse of(Map<String, String> fieldErrors, String message) {
        return ValidationResponse.builder()
                .validationErrors(fieldErrors)
                .message(message)
                .build();
    }
}
