package com.lawencon.jobportal.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.lawencon.jobportal.helper.ResponseHelper;
import com.lawencon.jobportal.model.response.LevelExperienceResponse;
import com.lawencon.jobportal.model.response.WebResponse;
import com.lawencon.jobportal.service.LevelExperienceService;
import org.springframework.http.MediaType;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.RolesAllowed;
import lombok.AllArgsConstructor;

@Tag(name = "Level Experience", description = "Level Experience API endpoint")
@RestController
@RequestMapping({"/api/v1"})
@AllArgsConstructor
public class LevelExperienceController {
    private final LevelExperienceService service;

    @RolesAllowed({"SA"})
    @GetMapping(value = "/level-experiences", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<List<LevelExperienceResponse>>> findAll() {
        return ResponseEntity.ok(ResponseHelper.ok(service.getAll()));
    }

    @RolesAllowed({"SA"})
    @GetMapping(value = "/level-experiences/{code}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<LevelExperienceResponse>> findById(
            @PathVariable String code) {
        return ResponseEntity.ok(ResponseHelper.ok(service.getByCode(code).orElse(null)));
    }
}
