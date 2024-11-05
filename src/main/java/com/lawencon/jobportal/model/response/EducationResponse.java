package com.lawencon.jobportal.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EducationResponse {
    private String id;
    private String univName;
    private String major;
    private String startDate;
    private String endDate;
}
