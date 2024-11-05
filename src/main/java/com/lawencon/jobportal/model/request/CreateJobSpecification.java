package com.lawencon.jobportal.model.request;

import com.lawencon.jobportal.persistence.entity.JobTitle;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateJobSpecification {
    @NotBlank
    private JobTitle jobTitle;

    @NotBlank
    private String specification;
}
