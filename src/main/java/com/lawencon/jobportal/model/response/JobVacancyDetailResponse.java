package com.lawencon.jobportal.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JobVacancyDetailResponse {
    private String id;
    private String jobVacancyId;
    private String picUserId;
    private Long version;
}
