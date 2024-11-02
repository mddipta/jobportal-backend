package com.lawencon.jobportal.controller;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.jobportal.helper.ResponseHelper;
import com.lawencon.jobportal.model.response.WebResponse;
import com.lawencon.jobportal.model.response.education.EducationResponse;
import com.lawencon.jobportal.service.EducationService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.lawencon.jobportal.model.request.education.CreateEducationRequest;
import com.lawencon.jobportal.model.request.education.UpdateEducationRequest;


@Tag(name = "Education", description = "Education API endpoint")
@RestController
@RequestMapping({"/api/v1"})
@AllArgsConstructor
public class EducationController {
    private final EducationService service;

    @GetMapping(value = "/educations", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<List<EducationResponse>>> getAll() {
        return ResponseEntity.ok(ResponseHelper.ok(service.getByUser()));
    }

    @PostMapping(value = "/educations", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<String>> create(@RequestBody CreateEducationRequest request) {
        service.create(request);
        return ResponseEntity.ok(ResponseHelper.ok("Success"));
    }

    @PutMapping(value = "/educations", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<String>> update(@RequestBody UpdateEducationRequest request) {
        service.update(request);
        return ResponseEntity.ok(ResponseHelper.ok("Success"));
    }

    @GetMapping(value = "/education/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<EducationResponse>> getById(@PathVariable String id) {
        return ResponseEntity.ok(ResponseHelper.ok(service.getById(id)));
    }

    @DeleteMapping(value = "/education/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<String>> delete(@PathVariable String id) {
        service.delete(id);
        return ResponseEntity.ok(ResponseHelper.ok("Success"));
    }
}
