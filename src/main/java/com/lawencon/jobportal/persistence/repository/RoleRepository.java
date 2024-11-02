package com.lawencon.jobportal.persistence.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lawencon.jobportal.persistence.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {
    Optional<Role> findByCode(String code);
}
