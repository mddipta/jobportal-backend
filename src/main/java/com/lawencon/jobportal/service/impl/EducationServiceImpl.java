package com.lawencon.jobportal.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.lawencon.jobportal.authentication.helper.SessionHelper;
import com.lawencon.jobportal.model.request.CreateEducationRequest;
import com.lawencon.jobportal.model.request.UpdateEducationRequest;
import com.lawencon.jobportal.model.response.EducationResponse;
import com.lawencon.jobportal.persistence.entity.Education;
import com.lawencon.jobportal.persistence.entity.User;
import com.lawencon.jobportal.persistence.repository.EducationRepository;
import com.lawencon.jobportal.service.EducationService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EducationServiceImpl implements EducationService {
    private final EducationRepository repository;

    @Override
    public List<EducationResponse> getByUser() {
        User currentUser = SessionHelper.getLoginUser();
        if (currentUser == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User is not authenticated");
        }

        List<EducationResponse> responses = new ArrayList<>();
        List<Education> educations = repository.findByUserId(currentUser.getId());
        educations.forEach(education -> responses.add(mapToResponse(education)));
        return responses;
    }

    @Override
    public void create(CreateEducationRequest request) {
        User currentUser = SessionHelper.getLoginUser();
        if (currentUser == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User is not authenticated");
        }

        Education education = new Education();
        education.setUser(currentUser);
        BeanUtils.copyProperties(request, education);
        repository.save(education);
    }

    @Override
    public void update(UpdateEducationRequest request) {
        Optional<Education> data = repository.findById(request.getId());

        if (data.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Education data not found");
        }

        Education education = data.get();
        BeanUtils.copyProperties(request, education);
        repository.save(education);
    }

    @Override
    public void delete(String id) {
        Optional<Education> data = repository.findById(id);

        if (data.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Education data not found");
        }

        repository.delete(data.get());
    }

    private EducationResponse mapToResponse(Education education) {
        EducationResponse response = new EducationResponse();
        response.setStartDate(education.getStartDate().toString());
        response.setEndDate(education.getEndDate().toString());
        BeanUtils.copyProperties(education, response);
        return response;
    }

    @Override
    public EducationResponse getById(String id) {
        Optional<Education> data = repository.findById(id);

        if (data.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Education data not found");
        }

        return mapToResponse(data.get());
    }
}
