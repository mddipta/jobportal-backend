package com.lawencon.jobportal.persistence.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lawencon.jobportal.persistence.entity.Otp;

@Repository
public interface OtpRepository extends JpaRepository<Otp, String> {
    Optional<Otp> findByCodeAndUserId(String code, String userId);
}
