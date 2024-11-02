package com.lawencon.jobportal.persistence.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lawencon.jobportal.persistence.entity.EmploymentType;

@Repository
public interface EmploymentTypeRepository extends JpaRepository<EmploymentType, String> {
    Optional<EmploymentType> findByCode(String code);
}
