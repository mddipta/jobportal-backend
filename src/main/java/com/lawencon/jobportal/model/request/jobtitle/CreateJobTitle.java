package com.lawencon.jobportal.model.request.jobtitle;

import java.util.List;

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
public class CreateJobTitle {
    @NotBlank
    @NotNull
    private String title;

    @NotBlank
    @NotNull
    private List<String> jobSpecification;

    @NotBlank
    @NotNull
    private List<String> jobDescription;
}
