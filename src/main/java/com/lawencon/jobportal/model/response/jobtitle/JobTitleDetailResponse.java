package com.lawencon.jobportal.model.response.jobtitle;

import java.util.List;

import com.lawencon.jobportal.model.response.jobdescription.JobDescriptionResponse;
import com.lawencon.jobportal.model.response.jobspecification.JobSpecificationResponse;

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
