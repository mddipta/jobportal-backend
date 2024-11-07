package com.lawencon.jobportal.persistence.repository;

import com.lawencon.jobportal.persistence.entity.OfferingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OfferingStatusRepository extends JpaRepository<OfferingStatus, String> {
    Optional<OfferingStatus> findByCode(String code);
}
