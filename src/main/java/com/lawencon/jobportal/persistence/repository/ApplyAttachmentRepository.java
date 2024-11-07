package com.lawencon.jobportal.persistence.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.lawencon.jobportal.persistence.entity.ApplyAttachment;

@Repository
public interface ApplyAttachmentRepository extends JpaRepository<ApplyAttachment, String> {
    List<ApplyAttachment> findByApplyCandidate_Id(String applyId);
}
