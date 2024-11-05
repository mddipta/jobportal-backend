package com.lawencon.jobportal.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lawencon.jobportal.persistence.entity.UserExperience;

@Repository
public interface UserExperienceRepository extends JpaRepository<UserExperience, String> {
    List<UserExperience> findByUserId(String userId);
}
