package com.lawencon.jobportal.model.request;

import java.time.LocalDate;

import com.lawencon.jobportal.persistence.entity.JobStatus;
import com.lawencon.jobportal.persistence.entity.JobVacancy;
import com.lawencon.jobportal.persistence.entity.User;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateJobVacancyTransactionRequest {
    @NotNull
    @NotBlank
    private JobVacancy jobVacancy;

    @NotNull
    @NotBlank
    private JobStatus jobStatus;

    @NotNull
    @NotBlank
    private User user;

    @NotNull
    @NotBlank
    private LocalDate date;

    @NotNull
    @NotBlank
    private Long number;
}
