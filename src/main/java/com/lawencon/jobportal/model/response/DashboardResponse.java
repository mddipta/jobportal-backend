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
public class DashboardResponse {
    private Long totalUser;
    private Long totalJobTitle;
    private Long totalVacancy;
    private Long totalOffering;
    private List<ProcessSelectionStageResponse> processSelectionStage;
    private List<JobVacancyResponse> jobVacancy;
}
