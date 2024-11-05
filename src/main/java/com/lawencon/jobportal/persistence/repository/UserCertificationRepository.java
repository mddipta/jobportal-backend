package com.lawencon.jobportal.persistence.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lawencon.jobportal.persistence.entity.UserCertification;

@Repository
public interface UserCertificationRepository extends JpaRepository<UserCertification, Object> {
    List<UserCertification> findByUserId(String userId);
}
