package com.lawencon.jobportal.persistence.repository;

import com.lawencon.jobportal.persistence.entity.StageProcessAttachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StageProcessAttachmentRepository extends JpaRepository<StageProcessAttachment, String> {
    StageProcessAttachment findByStageProcess_Id (String stageProcessId);
}
