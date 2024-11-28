package com.lawencon.jobportal.persistence.repository;

import java.util.Optional;

import com.lawencon.jobportal.persistence.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lawencon.jobportal.persistence.entity.UserProfile;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, String> {
    Optional<UserProfile> findByUserId(String userId);

    Optional<UserProfile> findByUser(User user);
}
