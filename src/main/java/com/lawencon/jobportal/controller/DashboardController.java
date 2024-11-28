package com.lawencon.jobportal.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.lawencon.jobportal.helper.ResponseHelper;
import com.lawencon.jobportal.model.response.DashboardResponse;
import com.lawencon.jobportal.model.response.WebResponse;
import com.lawencon.jobportal.service.DashboardService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.RolesAllowed;
import lombok.AllArgsConstructor;


@Tag(name = "Dashboard", description = "Dashboard API endpoint")
@RestController
@RequestMapping({"/api/v1"})
@AllArgsConstructor
public class DashboardController {
    private final DashboardService service;

    @RolesAllowed({"SA", "HR"})
    @GetMapping(value = "/dashboard", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<DashboardResponse>> get() {
        return ResponseEntity.ok(ResponseHelper.ok(service.getData()));
    }


}
