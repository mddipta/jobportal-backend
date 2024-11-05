package com.lawencon.jobportal.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserExperienceResponse {
    private String id;
    private String companyName;
    private String position;
    private String startDate;
    private String endDate;
}
