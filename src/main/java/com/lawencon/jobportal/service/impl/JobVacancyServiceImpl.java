package com.lawencon.jobportal.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import com.lawencon.jobportal.authentication.helper.SessionHelper;
import com.lawencon.jobportal.config.RabbitMQConfig;
import com.lawencon.jobportal.model.request.CreateJobVacancyRequest;
import com.lawencon.jobportal.model.request.CreateJobVacancyTransactionRequest;
import com.lawencon.jobportal.model.request.JobVacancyDetailRequest;
import com.lawencon.jobportal.model.request.PublishVacancyRequest;
import com.lawencon.jobportal.model.request.SetPicToVacancyRequest;
import com.lawencon.jobportal.model.request.UpdateJobVacancyRequest;
import com.lawencon.jobportal.model.request.UpdatePicJobVacancyRequest;
import com.lawencon.jobportal.model.request.UpdateStatusJobVacancyRequest;
import com.lawencon.jobportal.model.response.JobStatusResponse;
import com.lawencon.jobportal.model.response.JobVacancyOngoingResponse;
import com.lawencon.jobportal.model.response.JobVacancyResponse;
import com.lawencon.jobportal.persistence.entity.EmploymentType;
import com.lawencon.jobportal.persistence.entity.JobStatus;
import com.lawencon.jobportal.persistence.entity.JobTitle;
import com.lawencon.jobportal.persistence.entity.JobVacancy;
import com.lawencon.jobportal.persistence.entity.JobVacancyTransaction;
import com.lawencon.jobportal.persistence.entity.LevelExperience;
import com.lawencon.jobportal.persistence.entity.Location;
import com.lawencon.jobportal.persistence.entity.User;
import com.lawencon.jobportal.persistence.repository.JobVacancyRepository;
import com.lawencon.jobportal.service.EmploymentTypeService;
import com.lawencon.jobportal.service.JobStatusService;
import com.lawencon.jobportal.service.JobTitleService;
import com.lawencon.jobportal.service.JobVacancyDetailService;
import com.lawencon.jobportal.service.JobVacancyService;
import com.lawencon.jobportal.service.JobVacancyTransactionService;
import com.lawencon.jobportal.service.LevelExperienceService;
import com.lawencon.jobportal.service.LocationService;
import com.lawencon.jobportal.service.UserService;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class JobVacancyServiceImpl implements JobVacancyService {
    private final JobVacancyRepository repository;
    private final RabbitTemplate rabbitTemplate;

    private final JobTitleService jobTitleService;
    private final EmploymentTypeService employmentTypeService;
    private final LevelExperienceService levelExperienceService;
    private final LocationService locationService;
    private final JobVacancyTransactionService jobVacancyTransactionService;
    private final JobStatusService jobStatusService;
    private final UserService userService;
    private final JobVacancyDetailService jobVacancyDetailService;

    @Override
    public List<JobVacancyResponse> getAll() {
        List<JobVacancyResponse> responses = new ArrayList<>();
        List<JobVacancy> jobVacancies = repository.findAll();

        jobVacancies.forEach(jobVacancy -> {
            JobVacancyTransaction jobVacancyTransaction = transaction(jobVacancy.getId()).get();

            JobVacancyResponse response = mapToResponse(jobVacancy);

            response.setStatus(jobVacancyTransaction.getJobStatus().getStatus());
            responses.add(response);
        });

        return responses;
    }

    @Override
    public JobVacancyResponse getById(String id) {
        JobVacancy jobVacancy = repository.findById(id).orElse(null);

        Optional<JobVacancyTransaction> jobVacancyTransaction = transaction(id);
        JobVacancyResponse response = mapToResponse(jobVacancy);
        response.setStatus(jobVacancyTransaction.get().getJobStatus().getStatus());

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

    @Override
    public void setPicToVacancy(SetPicToVacancyRequest request) {
        CreateJobVacancyTransactionRequest createJobVacancyTransactionRequest =
                new CreateJobVacancyTransactionRequest();

        JobVacancy jobVacancy = getEntityById(request.getJobVacancyId());
        createJobVacancyTransactionRequest.setJobVacancy(jobVacancy);

        JobStatusResponse data = jobStatusService.getByCode("PD");
        JobStatus jobStatus = jobStatusService.getEntityById(data.getId());
        createJobVacancyTransactionRequest.setJobStatus(jobStatus);

        Optional<User> user = userService.getEntityById(request.getUserId());
        createJobVacancyTransactionRequest.setUser(user.get());

        createJobVacancyTransactionRequest.setDate(LocalDate.now());

        Optional<JobVacancyTransaction> jobVacancyTransaction =
                transaction(request.getJobVacancyId());
        if (jobVacancyTransaction.isPresent()) {
            createJobVacancyTransactionRequest
                    .setNumber(jobVacancyTransaction.get().getNumber() + 1);
        } else {
            createJobVacancyTransactionRequest.setNumber(1L);
        }

        JobVacancyDetailRequest jobVacancyDetailRequest = new JobVacancyDetailRequest();
        jobVacancyDetailRequest.setJobVacancy(jobVacancy);
        jobVacancyDetailRequest.setPicUser(user.get());

        jobVacancyTransactionService.create(createJobVacancyTransactionRequest);
        jobVacancyDetailService.create(jobVacancyDetailRequest);
    }

    @Override
    public void publishVacancy(PublishVacancyRequest request) {
        User currentUser = SessionHelper.getLoginUser();
        if (currentUser == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User is not authenticated");
        }

        CreateJobVacancyTransactionRequest createJobVacancyTransactionRequest =
                new CreateJobVacancyTransactionRequest();

        JobVacancy jobVacancy = getEntityById(request.getId());
        createJobVacancyTransactionRequest.setJobVacancy(jobVacancy);

        JobStatusResponse jobStatusResponse = jobStatusService.getByCode("OG");
        JobStatus jobStatus = jobStatusService.getEntityById(jobStatusResponse.getId());

        createJobVacancyTransactionRequest.setJobStatus(jobStatus);

        Optional<User> user = userService.getEntityById(currentUser.getId());
        createJobVacancyTransactionRequest.setUser(user.get());

        createJobVacancyTransactionRequest.setDate(request.getDeadline());
        Optional<JobVacancyTransaction> jobVacancyTransaction = transaction(request.getId());
        if (jobVacancyTransaction.isPresent()) {
            createJobVacancyTransactionRequest
                    .setNumber(jobVacancyTransaction.get().getNumber() + 1);
        } else {
            createJobVacancyTransactionRequest.setNumber(1L);
        }

        jobVacancyTransactionService.create(createJobVacancyTransactionRequest);

        JobVacancyResponse jobVacancyResponse = mapToResponse(jobVacancy);

        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_TOPIC, "vacancy.notif",
                jobVacancyResponse);
    }

    @Override
    public void updatePicToVacancy(UpdatePicJobVacancyRequest request) {
        JobVacancy jobVacancy = getEntityById(request.getJobVacancy());
        User user = userService.getEntityById(request.getPicUser()).get();
        if (jobVacancy == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "job vacancy does not exist");
        }

        if (user == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "user does not exist");
        }

        JobVacancyDetailRequest jobVacancyDetailRequest = new JobVacancyDetailRequest();
        jobVacancyDetailRequest.setJobVacancy(jobVacancy);
        jobVacancyDetailRequest.setPicUser(user);

        jobVacancyDetailService.update(jobVacancyDetailRequest);
    }

    @Override
    public List<JobVacancyOngoingResponse> getOpenVacancy() {
        List<JobVacancyOngoingResponse> responses = new ArrayList<>();
        List<JobVacancy> jobVacancies = repository.findAll();

        jobVacancies.forEach(jobVacancy -> {
            JobVacancyTransaction jobVacancyTransaction = transaction(jobVacancy.getId()).get();
            if (jobVacancyTransaction.getJobStatus().getCode().equals("OG")) {
                responses.add(mapToResponseOngoing(jobVacancy));
            }
        });

        return responses;
    }

    @Override
    public void changeStatus(UpdateStatusJobVacancyRequest request) {
        User currentUser = SessionHelper.getLoginUser();
        if (currentUser == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User is not authenticated");
        }

        CreateJobVacancyTransactionRequest createJobVacancyTransactionRequest =
                new CreateJobVacancyTransactionRequest();

        JobVacancy jobVacancy = getEntityById(request.getJobVacancyId());
        createJobVacancyTransactionRequest.setJobVacancy(jobVacancy);

        JobStatusResponse jobStatusResponse = jobStatusService.getByCode(request.getStatus());
        JobStatus jobStatus = jobStatusService.getEntityById(jobStatusResponse.getId());

        createJobVacancyTransactionRequest.setJobStatus(jobStatus);

        Optional<User> user = userService.getEntityById(currentUser.getId());
        createJobVacancyTransactionRequest.setUser(user.get());

        createJobVacancyTransactionRequest.setDate(LocalDate.now());
        Optional<JobVacancyTransaction> jobVacancyTransaction =
                transaction(request.getJobVacancyId());
        if (jobVacancyTransaction.isPresent()) {
            createJobVacancyTransactionRequest
                    .setNumber(jobVacancyTransaction.get().getNumber() + 1);
        } else {
            createJobVacancyTransactionRequest.setNumber(1L);
        }

        jobVacancyTransactionService.create(createJobVacancyTransactionRequest);

        JobVacancyResponse jobVacancyResponse = mapToResponse(jobVacancy);

        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_TOPIC, "vacancy.notif",
                jobVacancyResponse);
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

    private JobVacancyOngoingResponse mapToResponseOngoing(JobVacancy jobVacancy) {
        JobVacancyOngoingResponse response = new JobVacancyOngoingResponse();
        response.setTitleJob(jobVacancy.getJobTitle().getTitle());
        response.setEmploymentType(jobVacancy.getEmploymentType().getName());
        response.setLevelExperience(jobVacancy.getLevelExperience().getName());
        response.setLocation(jobVacancy.getLocation().getName());
        BeanUtils.copyProperties(jobVacancy, response);
        return response;
    }


    private Optional<JobVacancyTransaction> transaction(String id) {
        Optional<JobVacancyTransaction> transaction =
                jobVacancyTransactionService.getLastByJobVacancyId(id);
        if (transaction.isEmpty()) {
            Optional.empty();
        }
        return transaction;
    }



}
