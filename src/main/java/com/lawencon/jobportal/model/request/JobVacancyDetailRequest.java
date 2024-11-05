package com.lawencon.jobportal.model.request;

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
public class JobVacancyDetailRequest {
    @NotNull
    @NotBlank
    private JobVacancy jobVacancy;

    @NotNull
    @NotBlank
    private User picUser;
}
