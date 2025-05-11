package com.umutyenidil.librarymanagement.user;

import com.umutyenidil.librarymanagement.common.dto.response.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public void saveUser(User user) {

        userRepository.save(user);
    }

    public Page<User> findAllPatronUsers(Pageable pageable) {

        return userRepository.findAllWithPatronRole(pageable);
    }
}
