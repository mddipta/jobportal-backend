package com.lawencon.jobportal.persistence.entity;

import java.time.LocalDate;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@SuppressWarnings("deprecation")
@Entity
@Table(name = "tb_job_vacancies")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE tb_job_vacancies SET deleted_at = now() WHERE id=? AND version =?")
@Where(clause = "deleted_at IS NULL")
public class JobVacancy extends AuditableEntity {

    @ManyToOne
    @JoinColumn(name = "title_job_id", nullable = false, referencedColumnName = "id")
    private JobTitle jobTitle;

    @ManyToOne
    @JoinColumn(name = "employment_type_id", nullable = false, referencedColumnName = "id")
    private EmploymentType employmentType;

    @ManyToOne
    @JoinColumn(name = "level_experience_id", nullable = false, referencedColumnName = "id")
    private LevelExperience levelExperience;

    @ManyToOne
    @JoinColumn(name = "location_id", nullable = false, referencedColumnName = "id")
    private Location location;

    @Column(name = "overview", nullable = false)
    private String overview;

    @Column(name = "start_salary", nullable = false)
    private Long startSalary;

    @Column(name = "end_salary", nullable = false)
    private Long endSalary;

    @Column(name = "deadline_apply", nullable = false)
    private LocalDate deadlineApply;

}
