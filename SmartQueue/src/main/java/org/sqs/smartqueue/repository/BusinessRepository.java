package org.sqs.smartqueue.repository;

import org.sqs.smartqueue.data.model.Business;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BusinessRepository extends JpaRepository<Business, Long> {
    Optional<Business> findById(Long id);
    Optional<Business> findByName(String name);
}
