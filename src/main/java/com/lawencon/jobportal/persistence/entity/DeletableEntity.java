package com.lawencon.jobportal.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@MappedSuperclass
@Getter
@Setter
public class DeletableEntity extends AuditableEntity {
    @Column(name = "deleted_at")
    private ZonedDateTime deletedAt;
}
