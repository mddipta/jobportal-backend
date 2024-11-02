package com.lawencon.jobportal.model.request.jobdescription;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateJobDescription {
    @NotBlank
    private String id;

    @NotBlank
    private String description;
}
