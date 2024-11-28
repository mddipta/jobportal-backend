package com.lawencon.jobportal.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.lawencon.jobportal.persistence.entity.Offering;

@Repository
public interface OfferingRepository extends JpaRepository<Offering, String> {

    Long countBy();

}
