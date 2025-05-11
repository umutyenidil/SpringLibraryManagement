package com.umutyenidil.librarymanagement.user;

import com.umutyenidil.librarymanagement.auth.Auth;
import com.umutyenidil.librarymanagement.auth.AuthRepository;
import com.umutyenidil.librarymanagement.auth.AuthService;
import com.umutyenidil.librarymanagement.common.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final AuthRepository authRepository;

    public void saveUser(User user) {

        userRepository.save(user);
    }

    public Page<User> findAllPatronUsers(Pageable pageable) {

        return userRepository.findAllWithPatronRole(pageable);
    }

    public void updatePatronUser(UUID patronId, UserUpdateRequest request) {

        var existsPatron = authRepository.existsByIdAndRoleAndStatus(patronId, Auth.Role.PATRON, Auth.Status.ACTIVE);

        if (!existsPatron) throw new ResourceNotFoundException("error.patron.notfound");

        var user = userRepository.findById(patronId)
                .orElseThrow(() -> new ResourceNotFoundException("error.patron.notfound"));


        if (request.name().isPresent())
            user.setName(request.name().get());

        if (request.surname().isPresent())
            user.setSurname(request.surname().get());

        if (request.phone() != null)
            user.setPhone(request.phone());

        if (request.fullAddress() != null)
            user.setFullAddress(request.fullAddress());

        userRepository.save(user);
    }
}
