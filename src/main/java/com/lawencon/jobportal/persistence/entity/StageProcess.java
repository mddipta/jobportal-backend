package com.lawencon.jobportal.persistence.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.ZonedDateTime;

@SuppressWarnings("deprecation")
@Entity
@Table(name = "tb_stage_process")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE tb_stage_process SET deleted_at = now() WHERE id=? AND version =?")
@Where(clause = "deleted_at IS NULL")
public class StageProcess extends DeletableEntity {
    @ManyToOne
    @JoinColumn(name = "candidate_user_id", nullable = false)
    private User candidateUser;

    @ManyToOne
    @JoinColumn(name = "vacancy_job_id", nullable = false)
    private JobVacancy vacancyJob;

    @ManyToOne
    @JoinColumn(name = "stage_selection_id", nullable = false)
    private SelectionStage stageSelection;

    @ManyToOne
    @JoinColumn(name = "selection_status_id", nullable = false)
    private SelectionStageStatus statusSelection;

    @Column(name = "score", nullable = true)
    private Long score;

    @Column(name = "date", nullable = false)
    private ZonedDateTime date;

    @Column(name = "number", nullable = false)
    private Long number;
}
