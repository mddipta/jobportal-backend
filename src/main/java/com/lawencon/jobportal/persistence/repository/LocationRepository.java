package com.lawencon.jobportal.persistence.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lawencon.jobportal.persistence.entity.Location;

@Repository
public interface LocationRepository extends JpaRepository<Location, String> {
    Optional<Location> findByCode(String code);
}
