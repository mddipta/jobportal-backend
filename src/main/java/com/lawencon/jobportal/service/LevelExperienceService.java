package com.lawencon.jobportal.service;

import java.util.List;
import java.util.Optional;
import com.lawencon.jobportal.model.response.LevelExperienceResponse;
import com.lawencon.jobportal.persistence.entity.LevelExperience;

public interface LevelExperienceService {
    List<LevelExperienceResponse> getAll();

    Optional<LevelExperience> getEntityById(String id);

    Optional<LevelExperienceResponse> getByCode(String code);
}
