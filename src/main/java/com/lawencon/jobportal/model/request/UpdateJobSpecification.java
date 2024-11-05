package com.lawencon.jobportal.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateJobSpecification {
    @NotBlank
    private String id;

    @NotBlank
    private String specification;
}
