package com.lawencon.jobportal.service.impl;

import org.springframework.stereotype.Service;
import com.lawencon.jobportal.persistence.repository.OfferingRepository;
import com.lawencon.jobportal.service.OfferingService;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class OfferingServiceImpl implements OfferingService {
    private final OfferingRepository repository;

    @Override
    public Long countOffering() {
        return repository.countBy();
    }

}
