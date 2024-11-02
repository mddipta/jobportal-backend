package com.lawencon.jobportal.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.lawencon.jobportal.model.request.jobvacancy.CreateJobVacancyRequest;
import com.lawencon.jobportal.model.request.jobvacancy.UpdateJobVacancyRequest;
import com.lawencon.jobportal.model.response.jobvacancy.JobVacancyResponse;
import com.lawencon.jobportal.persistence.entity.EmploymentType;
import com.lawencon.jobportal.persistence.entity.JobTitle;
import com.lawencon.jobportal.persistence.entity.JobVacancy;
import com.lawencon.jobportal.persistence.entity.LevelExperience;
import com.lawencon.jobportal.persistence.entity.Location;
import com.lawencon.jobportal.persistence.repository.JobVacancyRepository;
import com.lawencon.jobportal.service.EmploymentTypeService;
import com.lawencon.jobportal.service.JobTitleService;
import com.lawencon.jobportal.service.JobVacancyService;
import com.lawencon.jobportal.service.LevelExperienceService;
import com.lawencon.jobportal.service.LocationService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class JobVacancyServiceImpl implements JobVacancyService {
    JobVacancyRepository repository;

    JobTitleService jobTitleService;
    EmploymentTypeService employmentTypeService;
    LevelExperienceService levelExperienceService;
    LocationService locationService;

    @Override
    public List<JobVacancyResponse> getAll() {
        List<JobVacancyResponse> responses = new ArrayList<>();
        List<JobVacancy> jobVacancies = repository.findAll();

        jobVacancies.forEach(jobVacancy -> {
            responses.add(mapToResponse(jobVacancy));
        });

        return responses;
    }

    @Override
    public JobVacancyResponse getById(String id) {
        JobVacancy jobVacancy = repository.findById(id).orElse(null);
        return mapToResponse(jobVacancy);
    }

    @Override
    public JobVacancy getEntityById(String id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public void create(CreateJobVacancyRequest request) {
        Optional<JobTitle> jobTitle = jobTitleService.getEntityById(request.getTitleJob());
        Optional<EmploymentType> employmentType =
                employmentTypeService.getEntityById(request.getEmploymentType());
        Optional<LevelExperience> levelExperience =
                levelExperienceService.getEntityById(request.getLevelExperience());
        Optional<Location> location = locationService.getEntityById(request.getLocation());

        if (jobTitle.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "job title does not exist");
        }

        if (employmentType.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "employment type does not exist");
        }

        if (levelExperience.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "level experience does not exist");
        }

        if (location.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "location does not exist");
        }

        JobVacancy jobVacancy = new JobVacancy();
        jobVacancy.setJobTitle(jobTitle.get());
        jobVacancy.setEmploymentType(employmentType.get());
        jobVacancy.setLevelExperience(levelExperience.get());
        jobVacancy.setLocation(location.get());
        jobVacancy.setOverview(request.getOverview());
        jobVacancy.setStartSalary(request.getStartSalary());
        jobVacancy.setEndSalary(request.getEndSalary());
        jobVacancy.setDeadlineApply(LocalDate.parse(request.getDeadlineApply()));

        repository.save(jobVacancy);

    }

    @Override
    public void update(UpdateJobVacancyRequest request) {
        Optional<JobVacancy> jobVacancy = repository.findById(request.getId());
        Optional<JobTitle> jobTitle = jobTitleService.getEntityById(request.getTitleJob());
        Optional<EmploymentType> employmentType =
                employmentTypeService.getEntityById(request.getEmploymentType());
        Optional<LevelExperience> levelExperience =
                levelExperienceService.getEntityById(request.getLevelExperience());
        Optional<Location> location = locationService.getEntityById(request.getLocation());

        if (jobVacancy.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "job vacancy does not exist");
        }

        if (jobTitle.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "job title does not exist");
        }

        if (employmentType.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "employment type does not exist");
        }

        if (levelExperience.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "level experience does not exist");
        }

        if (location.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "location does not exist");
        }

        JobVacancy update = jobVacancy.get();
        update.setJobTitle(jobTitle.get());
        update.setEmploymentType(employmentType.get());
        update.setLevelExperience(levelExperience.get());
        update.setLocation(location.get());
        update.setOverview(request.getOverview());
        update.setStartSalary(request.getStartSalary());
        update.setEndSalary(request.getEndSalary());
        update.setDeadlineApply(LocalDate.parse(request.getDeadlineApply()));
        update.setVersion(update.getVersion() + 1);

        repository.saveAndFlush(update);
    }

    @Override
    public void delete(String id) {
        repository.deleteById(id);
    }


    private JobVacancyResponse mapToResponse(JobVacancy jobVacancy) {
        JobVacancyResponse response = new JobVacancyResponse();
        response.setTitleJob(jobVacancy.getJobTitle().getTitle());
        response.setEmploymentType(jobVacancy.getEmploymentType().getName());
        response.setLevelExperience(jobVacancy.getLevelExperience().getName());
        response.setLocation(jobVacancy.getLocation().getName());
        BeanUtils.copyProperties(jobVacancy, response);
        return response;
    }
}
