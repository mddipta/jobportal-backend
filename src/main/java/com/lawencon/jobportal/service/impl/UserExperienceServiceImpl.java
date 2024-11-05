package com.lawencon.jobportal.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.lawencon.jobportal.authentication.helper.SessionHelper;
import com.lawencon.jobportal.model.request.CreateUserExperienceRequest;
import com.lawencon.jobportal.model.request.UpdateUserExperienceRequest;
import com.lawencon.jobportal.model.response.UserExperienceResponse;
import com.lawencon.jobportal.persistence.entity.User;
import com.lawencon.jobportal.persistence.entity.UserExperience;
import com.lawencon.jobportal.persistence.repository.UserExperienceRepository;
import com.lawencon.jobportal.service.UserExperienceService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserExperienceServiceImpl implements UserExperienceService {
    private final UserExperienceRepository repository;


    @Override
    public List<UserExperienceResponse> getByUserId() {
        User user = SessionHelper.getLoginUser();

        if (user == null) {
            throw new UnsupportedOperationException("User is not authenticated");
        }

        List<UserExperienceResponse> responses = new ArrayList<>();
        List<UserExperience> userExperiences = repository.findByUserId(user.getId());
        userExperiences.forEach(data -> {
            responses.add(mapToResponse(data));
        });

        return responses;
    }

    @Override
    public void create(CreateUserExperienceRequest request) {
        User user = SessionHelper.getLoginUser();

        if (user == null) {
            throw new UnsupportedOperationException("User is not authenticated");
        }

        UserExperience userExperience = new UserExperience();
        userExperience.setUser(user);
        userExperience.setCompanyName(request.getCompanyName());
        userExperience.setPosition(request.getPosition());
        userExperience.setStartDate(request.getStartDate());
        userExperience.setEndDate(request.getEndDate());

        repository.saveAndFlush(userExperience);
    }

    @Override
    public void update(UpdateUserExperienceRequest request) {
        User user = SessionHelper.getLoginUser();

        if (user == null) {
            throw new UnsupportedOperationException("User is not authenticated");
        }

        Optional<UserExperience> data = repository.findById(request.getId());
        if (data.isEmpty()) {
            throw new UnsupportedOperationException("User experience data not found");
        }

        UserExperience userExperience = data.get();
        userExperience.setCompanyName(request.getCompanyName());
        userExperience.setPosition(request.getPosition());
        userExperience.setStartDate(request.getStartDate());
        userExperience.setEndDate(request.getEndDate());

        repository.saveAndFlush(userExperience);
    }

    @Override
    public void delete(String id) {
        User user = SessionHelper.getLoginUser();

        if (user == null) {
            throw new UnsupportedOperationException("User is not authenticated");
        }

        Optional<UserExperience> data = repository.findById(id);
        if (data.isEmpty()) {
            throw new UnsupportedOperationException("User experience data not found");
        }

        repository.delete(data.get());
    }

    @Override
    public UserExperienceResponse getById(String id) {
        User user = SessionHelper.getLoginUser();

        if (user == null) {
            throw new UnsupportedOperationException("User is not authenticated");
        }

        Optional<UserExperience> data = repository.findById(id);
        if (data.isEmpty()) {
            throw new UnsupportedOperationException("User experience data not found");
        }

        return mapToResponse(data.get());
    }

    private UserExperienceResponse mapToResponse(UserExperience userExperience) {
        UserExperienceResponse response = new UserExperienceResponse();
        response.setStartDate(userExperience.getStartDate().toString());
        response.setEndDate(userExperience.getEndDate().toString());
        BeanUtils.copyProperties(userExperience, response);
        return response;
    }


}
