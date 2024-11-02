package com.lawencon.jobportal.persistence.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lawencon.jobportal.persistence.entity.LevelExperience;

@Repository
public interface LevelExperienceRepository extends JpaRepository<LevelExperience, String> {
    Optional<LevelExperience> findByCode(String code);
}
