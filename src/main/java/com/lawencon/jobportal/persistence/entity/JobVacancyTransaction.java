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
@Table(name = "tb_job_vacancies_transactions")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(
        sql = "UPDATE tb_job_vacancies_transactions SET deleted_at = now() WHERE id=? AND version =?")
@Where(clause = "deleted_at IS NULL")
public class JobVacancyTransaction extends DeletableEntity {
    @ManyToOne
    @JoinColumn(name = "job_vacancy_id", nullable = false)
    private JobVacancy jobVacancy;

    @ManyToOne
    @JoinColumn(name = "job_status_id", nullable = false)
    private JobStatus jobStatus;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "number", nullable = false)
    private Long number;
}
