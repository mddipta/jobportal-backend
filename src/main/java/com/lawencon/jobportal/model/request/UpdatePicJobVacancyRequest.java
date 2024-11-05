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
public class UpdatePicJobVacancyRequest {
    @NotNull
    @NotBlank
    private String picUser;

    @NotNull
    @NotBlank
    private String jobVacancy;
}
