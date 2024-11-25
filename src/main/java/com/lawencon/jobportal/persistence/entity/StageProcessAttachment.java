package com.lawencon.jobportal.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@SuppressWarnings("deprecation")
@Entity
@Table(name = "tb_stage_process_attachments")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE tb_stage_process_attachments SET deleted_at = now() WHERE id=? AND version =?")
@Where(clause = "deleted_at IS NULL")
public class StageProcessAttachment extends DeletableEntity{
    @OneToOne
    @JoinColumn(name = "stage_process_id", nullable = false, referencedColumnName = "id")
    private StageProcess stageProcess;

    @OneToOne
    @JoinColumn(name = "question_file_id", nullable = false, referencedColumnName = "id")
    private File questionFile;

    @OneToOne
    @JoinColumn(name = "answer_file_id", nullable = false, referencedColumnName = "id")
    private File answerFile;

}
