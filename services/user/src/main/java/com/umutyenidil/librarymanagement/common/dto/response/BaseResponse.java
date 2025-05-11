package com.umutyenidil.librarymanagement.common.dto.response;

import lombok.*;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class BaseResponse<T> {

    private final boolean success;
    private final LocalDateTime timestamp;
    private final String message;
    private final T data;
    private final Object metaData;

    protected BaseResponse(
            boolean success,
            LocalDateTime timestamp,
            String message,
            T data,
            Object metaData
    ) {
        this.success = success;
        this.timestamp = timestamp != null ? timestamp : LocalDateTime.now();
        this.message = message;
        this.data = data;
        this.metaData = metaData;
    }

    protected BaseResponse(
            boolean success,
            String message,
            T data,
            Object metaData
    ) {
        this(success, LocalDateTime.now(), message, data, metaData);
    }
}
