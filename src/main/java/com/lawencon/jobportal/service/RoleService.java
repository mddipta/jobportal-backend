package com.lawencon.jobportal.service;

import com.lawencon.jobportal.model.response.role.RoleResponse;
import com.lawencon.jobportal.persistence.entity.Role;

import java.util.List;
import java.util.Optional;

public interface RoleService {
    List<RoleResponse> getAll();

    Optional<Role> getByCode(String code);

    Optional<Role> getEntityById(String id);
}
