package org.ipt.smartqueue.repository;

import org.ipt.smartqueue.data.dto.UserDto;
import org.ipt.smartqueue.data.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<UserDto> findById(UUID id);
    Optional<User> findByEmail(String email);
}
