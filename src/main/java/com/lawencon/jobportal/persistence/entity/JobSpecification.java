package com.lawencon.jobportal.persistence.entity;

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
@Table(name = "tb_job_spesifications")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE tb_job_spesifications SET deleted_at = now() WHERE id=? AND version =?")
@Where(clause = "deleted_at IS NULL")
public class JobSpecification extends DeletableEntity {
    @ManyToOne
    @JoinColumn(name = "title_job_id", nullable = false, referencedColumnName = "id")
    private JobTitle jobTitle;

    @Column(name = "spesification", nullable = false)
    private String spesification;
}
