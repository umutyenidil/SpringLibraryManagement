package com.umutyenidil.librarymanagement.user;

import org.springframework.stereotype.Service;

@Service
public class UserMapper {

    UserResponse toResponse(User user) {

        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .surname(user.getSurname())
                .phone(user.getPhone())
                .fullAddress(user.getFullAddress())
                .build();
    }
}
