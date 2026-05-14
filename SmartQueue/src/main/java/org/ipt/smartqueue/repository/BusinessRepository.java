package org.ipt.smartqueue.repository;

import org.ipt.smartqueue.data.dto.BusinessDto;
import org.ipt.smartqueue.data.model.Business;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BusinessRepository extends JpaRepository<Business, Long> {
    Optional<Business> findById(Long id);
    Optional<BusinessDto> findByName(String name);
}
