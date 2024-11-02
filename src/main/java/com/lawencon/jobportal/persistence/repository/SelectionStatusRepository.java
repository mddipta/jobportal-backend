package com.lawencon.jobportal.persistence.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.lawencon.jobportal.persistence.entity.SelectionStageStatus;

@Repository
public interface SelectionStatusRepository extends JpaRepository<SelectionStageStatus, String> {
    Optional<SelectionStageStatus> findByCode(String code);
}
