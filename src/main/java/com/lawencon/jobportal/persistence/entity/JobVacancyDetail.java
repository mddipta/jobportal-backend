package com.lawencon.jobportal.persistence.entity;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

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
@Table(name = "tb_job_vacancy_detail")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE tb_job_vacancy_detail SET deleted_at = now() WHERE id=? AND version =?")
@Where(clause = "deleted_at IS NULL")
public class JobVacancyDetail extends DeletableEntity {
    @ManyToOne
    @JoinColumn(name = "vacancy_id", nullable = false, referencedColumnName = "id")
    private JobVacancy jobVacancy;

    @ManyToOne
    @JoinColumn(name = "pic_user_id", nullable = false, referencedColumnName = "id")
    private User picUser;
}
