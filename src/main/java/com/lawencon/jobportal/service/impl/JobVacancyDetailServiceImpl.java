package com.lawencon.jobportal.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import com.lawencon.jobportal.model.request.JobVacancyDetailRequest;
import com.lawencon.jobportal.model.response.JobVacancyDetailResponse;
import com.lawencon.jobportal.persistence.entity.JobVacancyDetail;
import com.lawencon.jobportal.persistence.repository.JobVacancyDetailRepository;
import com.lawencon.jobportal.service.JobVacancyDetailService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class JobVacancyDetailServiceImpl implements JobVacancyDetailService {

    private final JobVacancyDetailRepository jobVacancyDetailRepository;

    @Override
    public void create(JobVacancyDetailRequest request) {
        JobVacancyDetail jobVacancyDetail = new JobVacancyDetail();
        jobVacancyDetail.setPicUser(request.getPicUser());
        jobVacancyDetail.setJobVacancy(request.getJobVacancy());

        jobVacancyDetailRepository.saveAndFlush(jobVacancyDetail);
    }

    @Override
    public void update(JobVacancyDetailRequest request) {
        Optional<JobVacancyDetail> jobVacancyDetail =
                jobVacancyDetailRepository.findByJobVacancy_IdAndPicUser_Id(
                        request.getJobVacancy().getId(), request.getPicUser().getId());
        if (jobVacancyDetail.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Data not found");
        }

        JobVacancyDetail updatedJobVacancyDetail = jobVacancyDetail.get();
        updatedJobVacancyDetail.setJobVacancy(request.getJobVacancy());
        updatedJobVacancyDetail.setPicUser(request.getPicUser());

        jobVacancyDetailRepository.saveAndFlush(updatedJobVacancyDetail);
    }

    @Override
    public List<JobVacancyDetailResponse> getByJobVacancyId(String id) {
        List<JobVacancyDetailResponse> responses = new ArrayList<>();
        List<JobVacancyDetail> jobVacancyDetails =
                jobVacancyDetailRepository.findByJobVacancyId(id);

        jobVacancyDetails.forEach(jobVacancyDetail -> {
            responses.add(mapToResponse(jobVacancyDetail));
        });
        return responses;
    }

    @Override
    public List<JobVacancyDetailResponse> getByPicUserId(String id) {
        List<JobVacancyDetailResponse> responses = new ArrayList<>();
        List<JobVacancyDetail> jobVacancyDetails = jobVacancyDetailRepository.findByPicUserId(id);

        jobVacancyDetails.forEach(jobVacancyDetail -> {
            responses.add(mapToResponse(jobVacancyDetail));
        });
        return responses;
    }

    @Override
    public void deleteByJobVacancyId(String id) {
        jobVacancyDetailRepository.deleteByJobVacancy_Id(id);
    }

    @Override
    public void deleteByUserId(String id) {
        jobVacancyDetailRepository.deleteByPicUser_Id(id);
    }

    private JobVacancyDetailResponse mapToResponse(JobVacancyDetail jobVacancyDetail) {
        JobVacancyDetailResponse response = new JobVacancyDetailResponse();
        response.setPicUserId(jobVacancyDetail.getPicUser().getId());
        response.setJobVacancyId(jobVacancyDetail.getJobVacancy().getId());
        BeanUtils.copyProperties(jobVacancyDetail, response);
        return response;
    }


}
