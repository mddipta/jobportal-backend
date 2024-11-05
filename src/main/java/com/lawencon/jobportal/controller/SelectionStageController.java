package com.lawencon.jobportal.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.MediaType;
import com.lawencon.jobportal.helper.ResponseHelper;
import com.lawencon.jobportal.model.response.SelectionStageResponse;
import com.lawencon.jobportal.model.response.WebResponse;
import com.lawencon.jobportal.service.SelectionStageService;

import java.util.List;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.RolesAllowed;
import lombok.AllArgsConstructor;

@Tag(name = "Selection Stage", description = "Selection Stage API endpoint")
@RestController
@RequestMapping({"/api/v1"})
@AllArgsConstructor
public class SelectionStageController {
    private final SelectionStageService service;

    @RolesAllowed({"SA"})
    @GetMapping(value = "/selection-stages", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<List<SelectionStageResponse>>> findAll() {
        return ResponseEntity.ok(ResponseHelper.ok(service.getAll()));
    }

    @RolesAllowed({"SA"})
    @GetMapping(value = "/selection-stages/{code}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<SelectionStageResponse>> findById(@PathVariable String code) {
        return ResponseEntity.ok(ResponseHelper.ok(service.getByCode(code)));
    }
}
