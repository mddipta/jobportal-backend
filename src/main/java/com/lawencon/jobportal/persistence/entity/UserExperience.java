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
@Table(name = "tb_user_experiences")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE tb_user_experiences SET deleted_at = now() WHERE id=? AND version =?")
@Where(clause = "deleted_at IS NULL")
public class UserExperience extends DeletableEntity {
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, referencedColumnName = "id")
    private User user;

    @Column(name = "company_name", nullable = false)
    private String companyName;

    @Column(name = "position", nullable = false)
    private String position;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;
}
