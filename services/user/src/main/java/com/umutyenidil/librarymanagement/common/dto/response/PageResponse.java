package com.umutyenidil.librarymanagement.common.dto.response;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Getter
public class PageResponse<T> extends BaseResponse<List<T>> {

    @Builder
    public PageResponse(
            String message,
            List<T> data,
            int page,
            int size,
            long totalElements,
            int totalPages
    ) {
        super(
                true,
                LocalDateTime.now(),
                message,
                data,
                Map.of(
                        "page", page,
                        "size", size,
                        "totalElements", totalElements,
                        "totalPages", totalPages
                )
        );
    }

    public static <T> PageResponse<T> fromPage(Page<T> page, String message) {
        return PageResponse.<T>builder()
                .message(message)
                .data(page.getContent())
                .page(page.getNumber())
                .size(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .build();
    }

    public static <T> PageResponse<T> fromPage(Page<T> page) {
        return fromPage(page, null);
    }
}
