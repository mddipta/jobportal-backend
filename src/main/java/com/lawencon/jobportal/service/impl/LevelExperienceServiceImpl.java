package com.lawencon.jobportal.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.lawencon.jobportal.model.response.levelexperience.LevelExperienceResponse;
import com.lawencon.jobportal.persistence.entity.LevelExperience;
import com.lawencon.jobportal.persistence.repository.LevelExperienceRepository;
import com.lawencon.jobportal.service.LevelExperienceService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class LevelExperienceServiceImpl implements LevelExperienceService {
    LevelExperienceRepository repository;

    @Override
    public List<LevelExperienceResponse> getAll() {
        List<LevelExperienceResponse> responses = new ArrayList<>();
        List<LevelExperience> levelExperiences = repository.findAll();
        levelExperiences.forEach(data -> responses.add(mapToResponse(data)));
        return responses;
    }

    @Override
    public Optional<LevelExperience> getEntityById(String id) {
        return repository.findById(id);
    }

    @Override
    public Optional<LevelExperienceResponse> getByCode(String code) {
        Optional<LevelExperience> levelExperience = repository.findByCode(code);
        if (levelExperience == null) {
            return Optional.empty();
        }
        return Optional.of(mapToResponse(levelExperience.get()));
    }

    private LevelExperienceResponse mapToResponse(LevelExperience levelExperience) {
        LevelExperienceResponse response = new LevelExperienceResponse();
        BeanUtils.copyProperties(levelExperience, response);
        return response;
    }


}
