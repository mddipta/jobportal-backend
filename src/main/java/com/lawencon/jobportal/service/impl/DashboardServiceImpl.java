package com.lawencon.jobportal.service.impl;

import java.util.List;
import org.springframework.stereotype.Service;
import com.lawencon.jobportal.model.response.DashboardResponse;
import com.lawencon.jobportal.model.response.JobVacancyResponse;
import com.lawencon.jobportal.service.DashboardService;
import com.lawencon.jobportal.service.JobTitleService;
import com.lawencon.jobportal.service.JobVacancyService;
import com.lawencon.jobportal.service.OfferingService;
import com.lawencon.jobportal.service.StageProcessService;
import com.lawencon.jobportal.model.response.ProcessSelectionStageResponse;
import com.lawencon.jobportal.service.UserService;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class DashboardServiceImpl implements DashboardService {
    private final UserService userService;
    private final JobTitleService jobTitleService;
    private final JobVacancyService jobVacancyService;
    private final OfferingService offeringService;
    private final StageProcessService stageProcessService;

    @Override
    public DashboardResponse getData() {
        Long userCount = userService.countUser();
        Long jobTitleCount = jobTitleService.countJobTitle();
        Long jobVacancyCount = jobVacancyService.countJobVacancy();
        Long offeringCount = offeringService.countOffering();
        List<ProcessSelectionStageResponse> selectionStage = stageProcessService.getTopSelection();
        List<JobVacancyResponse> jobVacancy = jobVacancyService.getNewestVacancy();

        DashboardResponse response = new DashboardResponse();
        response.setTotalUser(userCount);
        response.setTotalJobTitle(jobTitleCount);
        response.setTotalVacancy(jobVacancyCount);
        response.setTotalOffering(offeringCount);
        response.setProcessSelectionStage(selectionStage);
        response.setJobVacancy(jobVacancy);

        return response;
    }

}
