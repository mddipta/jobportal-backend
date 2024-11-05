package com.lawencon.jobportal.controller;

import java.util.List;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.lawencon.jobportal.helper.ResponseHelper;
import com.lawencon.jobportal.model.response.EmploymentTypeResponse;
import com.lawencon.jobportal.model.response.WebResponse;
import com.lawencon.jobportal.service.EmploymentTypeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@Tag(name = "Employment Type", description = "Employment Type API endpoint")
@RestController
@RequestMapping({"/api/v1"})
@AllArgsConstructor
public class EmploymentTypeController {
    private final EmploymentTypeService service;

    @GetMapping(value = "/employment-types", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<List<EmploymentTypeResponse>>> findAll() {
        return ResponseEntity.ok(ResponseHelper.ok(service.getAll()));
    }

    @GetMapping(value = "/employment-types/{code}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<EmploymentTypeResponse>> findById(@PathVariable String code) {
        return ResponseEntity.ok(ResponseHelper.ok(service.getByCode(code).orElse(null)));
    }
}
