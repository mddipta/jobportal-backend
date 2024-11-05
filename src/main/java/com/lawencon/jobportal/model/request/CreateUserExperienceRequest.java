package com.lawencon.jobportal.model.request;

import java.time.LocalDate;

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
public class CreateUserExperienceRequest {
    @NotNull
    @NotBlank
    private String companyName;

    @NotNull
    @NotBlank
    private String position;

    @NotNull
    @NotBlank
    private LocalDate startDate;

    @NotNull
    @NotBlank
    private LocalDate endDate;
}
