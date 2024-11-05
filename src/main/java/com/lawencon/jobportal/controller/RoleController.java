package com.lawencon.jobportal.controller;

import java.util.List;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.lawencon.jobportal.helper.ResponseHelper;
import com.lawencon.jobportal.model.response.RoleResponse;
import com.lawencon.jobportal.model.response.WebResponse;
import com.lawencon.jobportal.service.RoleService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.RolesAllowed;
import lombok.AllArgsConstructor;

@Tag(name = "Role", description = "Role API endpoint")
@RestController
@RequestMapping({"/api/v1"})
@AllArgsConstructor
public class RoleController {
    private final RoleService roleService;

    @RolesAllowed({"SA", "HR"})
    @GetMapping(value = "/roles", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<List<RoleResponse>>> findAll() {
        return ResponseEntity.ok(ResponseHelper.ok(roleService.getAll()));
    }

}
