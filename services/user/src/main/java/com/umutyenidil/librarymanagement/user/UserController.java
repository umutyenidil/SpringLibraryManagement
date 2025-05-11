package com.umutyenidil.librarymanagement.user;

import com.umutyenidil.librarymanagement.auth.PatronValidationGroup;
import com.umutyenidil.librarymanagement.common.dto.response.MessageResponse;
import com.umutyenidil.librarymanagement.common.dto.response.PageResponse;
import com.umutyenidil.librarymanagement.common.dto.response.SuccessResponse;
import com.umutyenidil.librarymanagement.common.util.MessageUtil;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
@RestController
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;
    private final MessageUtil messageUtil;

    @PreAuthorize("hasRole('LIBRARIAN')")
    @GetMapping
    public ResponseEntity<PageResponse<UserResponse>> findAllPatronUsers(
            @RequestParam(defaultValue = "1") @Positive Integer page,
            @RequestParam(defaultValue = "20") @Positive Integer size
    ) {
        return ResponseEntity.ok(
                PageResponse.fromPage(
                        userService.findAllPatronUsers(PageRequest.of(page - 1, size))
                                .map(userMapper::toResponse)
                )
        );
    }

    @PreAuthorize("hasRole('LIBRARIAN')")
    @PutMapping("/{user-id}")
    public ResponseEntity<MessageResponse> updatePatronUser(
            @PathVariable("user-id") UUID userId,
            @RequestBody @Validated(PatronValidationGroup.class) UserUpdateRequest request
    ) {

        userService.updatePatronUser(userId, request);

        return ResponseEntity.ok(MessageResponse.of(messageUtil.getMessage("success.patron.update")));
    }

    @PreAuthorize("hasRole('LIBRARIAN')")
    @DeleteMapping("/{user-id}")
    public ResponseEntity<MessageResponse> deletePatronUser(
            @PathVariable("user-id") UUID userId
    ) {

        userService.deletePatronUser(userId);

        return ResponseEntity.ok(MessageResponse.of(messageUtil.getMessage("success.patron.delete")));
    }
}
