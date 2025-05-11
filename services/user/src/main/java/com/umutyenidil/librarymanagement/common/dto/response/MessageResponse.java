package com.umutyenidil.librarymanagement.common.dto.response;

import java.util.List;

public class MessageResponse extends BaseResponse<Void> {

    private MessageResponse(boolean success, String message, Object metaData) {
        super(success, message, null, metaData);
    }

    public static MessageResponse of(String message) {
        return new MessageResponse(true, message, null);
    }

    public static MessageResponse of(String message, boolean success) {
        return new MessageResponse(success, message, null);
    }

    public static MessageResponse of(List<String> messages) {
        return new MessageResponse(true, null, messages);
    }

    public static MessageResponse of(List<String> messages, boolean success) {
        return new MessageResponse(success, null, messages);
    }
}
