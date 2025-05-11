package com.umutyenidil.librarymanagement.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    @Query("""
                SELECT u FROM User u
                JOIN Auth a ON u.id = a.id
                WHERE a.role = 'PATRON' AND a.deletedAt IS NULL
            """)
    Page<User> findAllWithPatronRole(Pageable pageable);
}
