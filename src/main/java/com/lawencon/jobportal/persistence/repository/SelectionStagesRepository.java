package com.lawencon.jobportal.persistence.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lawencon.jobportal.persistence.entity.SelectionStage;

@Repository
public interface SelectionStagesRepository extends JpaRepository<SelectionStage, String> {
    Optional<SelectionStage> findByCode(String code);
}
