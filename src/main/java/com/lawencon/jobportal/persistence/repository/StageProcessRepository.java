package com.lawencon.jobportal.persistence.repository;

import com.lawencon.jobportal.persistence.entity.StageProcess;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StageProcessRepository extends JpaRepository<StageProcess, String> {
    List<StageProcess> findByCandidateUser_Id (String userId);

    List<StageProcess> findByVacancyJob_Id (String jobId);

    List<StageProcess> findByVacancyJob_IdAndCandidateUser_Id (String jobId, String userId);
}