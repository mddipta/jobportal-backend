package com.lawencon.jobportal.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import com.lawencon.jobportal.model.response.RoleResponse;
import com.lawencon.jobportal.persistence.entity.Role;
import com.lawencon.jobportal.persistence.repository.RoleRepository;
import com.lawencon.jobportal.service.RoleService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Override
    public List<RoleResponse> getAll() {
        List<RoleResponse> responses = new ArrayList<>();
        List<Role> roles = roleRepository.findAll();
        roles.forEach(role -> responses.add(mapToResponse(role)));
        return responses;
    }

    @Override
    public Optional<Role> getByCode(String code) {
        return roleRepository.findByCode(code);
    }

    @Override
    public Optional<Role> getEntityById(String id) {
        return roleRepository.findById(id);
    }

    private RoleResponse mapToResponse(Role role) {
        RoleResponse response = new RoleResponse();
        BeanUtils.copyProperties(role, response);
        return response;
    }
}
