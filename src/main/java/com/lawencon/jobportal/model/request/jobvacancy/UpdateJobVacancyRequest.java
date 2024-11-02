package com.lawencon.jobportal.model.request.jobvacancy;

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
public class UpdateJobVacancyRequest {
    @NotBlank
    @NotNull
    private String id;

    @NotNull
    @NotBlank
    private String titleJob;

    @NotNull
    @NotBlank
    private String employmentType;

    @NotNull
    @NotBlank
    private String levelExperience;

    @NotNull
    @NotBlank
    private String location;

    @NotNull
    @NotBlank
    private String overview;

    @NotNull
    private Long startSalary;

    @NotNull
    private Long endSalary;

    @NotNull
    private String deadlineApply;
}
