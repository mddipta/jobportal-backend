package com.lawencon.jobportal.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JobVacancyOngoingResponse {
    private String id;
    private String titleJob;
    private String employmentType;
    private String levelExperience;
    private String location;
    private String overview;
    private Long startSalary;
    private Long endSalary;
    private String deadlineApply;
}
