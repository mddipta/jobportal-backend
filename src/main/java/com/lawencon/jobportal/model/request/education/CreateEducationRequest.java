package com.lawencon.jobportal.model.request.education;

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
public class CreateEducationRequest {
    @NotNull
    @NotBlank
    private String univName;

    @NotNull
    @NotBlank
    private String major;

    @NotNull
    @NotBlank
    private LocalDate startDate;

    @NotNull
    @NotBlank
    private LocalDate endDate;
}
