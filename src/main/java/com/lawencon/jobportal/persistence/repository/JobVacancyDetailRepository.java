package com.lawencon.jobportal.persistence.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lawencon.jobportal.persistence.entity.JobVacancyDetail;

@Repository
public interface JobVacancyDetailRepository extends JpaRepository<JobVacancyDetail, String> {
    List<JobVacancyDetail> findByPicUserId(String userId);

    List<JobVacancyDetail> findByJobVacancyId(String jobVacancyId);

    Optional<JobVacancyDetail> findByJobVacancy_IdAndPicUser_Id(String jobVacancyId,
            String picUserId);

    void deleteByJobVacancy_Id(String jobVacancyId);

    void deleteByPicUser_Id(String picUserId);
}
