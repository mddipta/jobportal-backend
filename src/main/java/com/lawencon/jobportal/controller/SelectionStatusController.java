package com.lawencon.jobportal.controller;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.jobportal.helper.ResponseHelper;
import com.lawencon.jobportal.model.response.WebResponse;
import com.lawencon.jobportal.model.response.selectionstatus.SelectionStatusResponse;
import com.lawencon.jobportal.service.SelectionStatusService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.RolesAllowed;
import lombok.AllArgsConstructor;


@Tag(name = "Selection Stage Status", description = "Selection Stage Status API endpoint")
@RestController
@RequestMapping({"/api/v1"})
@AllArgsConstructor
public class SelectionStatusController {
    SelectionStatusService service;

    @RolesAllowed({"SA"})
    @GetMapping(value = "/selection-stage-statuses", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<List<SelectionStatusResponse>>> findAll() {
        return ResponseEntity.ok(ResponseHelper.ok(service.getAll()));
    }

    @RolesAllowed({"SA"})
    @GetMapping(value = "/selection-stage-status/{code}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<SelectionStatusResponse>> findById(
            @PathVariable String code) {
        return ResponseEntity.ok(ResponseHelper.ok(service.getByCode(code).orElse(null)));
    }
}
