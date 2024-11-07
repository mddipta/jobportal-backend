package com.lawencon.jobportal.persistence.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.lawencon.jobportal.persistence.entity.ApplyCandidate;

@Repository
public interface ApplyCandidateRepository extends JpaRepository<ApplyCandidate, String> {
    List<ApplyCandidate> findByUserId(String userId);

    Optional<ApplyCandidate> findByJobVacancy_Id(String vacancyId);
}
