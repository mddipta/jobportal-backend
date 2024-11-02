package com.lawencon.jobportal.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@SuppressWarnings("deprecation")
@Entity
@Table(name = "tb_selection_statuses",
        uniqueConstraints = {
                @UniqueConstraint(name = "selection_stage_bk", columnNames = {"code"})})
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE tb_selection_statuses SET deleted_at = now() WHERE id=? AND version =?")
@Where(clause = "deleted_at IS NULL")
public class SelectionStageStatus extends MasterEntity {
    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "name", nullable = false)
    private String name;
}

