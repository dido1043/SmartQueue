package org.sqs.smartqueue.repository;

import org.sqs.smartqueue.data.dto.UserDto;
import org.sqs.smartqueue.data.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<UserDto> findById(UUID id);
    Optional<User> findByEmail(String email);
    Optional<User> findByName(String name);
}
