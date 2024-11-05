package com.lawencon.jobportal.model.request;

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
public class UpdateEducationRequest {
    @NotNull
    @NotBlank
    private String id;

    @NotNull
    @NotBlank
    private String univName;

    @NotNull
    @NotBlank
    private String major;

    @NotNull
    @NotBlank
    private String startDate;

    @NotNull
    @NotBlank
    private String endDate;
}
