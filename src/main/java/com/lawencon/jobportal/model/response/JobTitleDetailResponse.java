package com.lawencon.jobportal.model.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JobTitleDetailResponse {
    private String id;
    private String title;
    private List<JobSpecificationResponse> jobSpecifications;
    private List<JobDescriptionResponse> jobDescriptions;
}
