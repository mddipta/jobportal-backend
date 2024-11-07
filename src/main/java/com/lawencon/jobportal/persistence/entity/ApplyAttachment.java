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
@Table(name = "tb_apply_attachments")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE tb_apply_attachments SET deleted_at = now() WHERE id=? AND version =?")
@Where(clause = "deleted_at IS NULL")
public class ApplyAttachment extends DeletableEntity {
    @ManyToOne
    @JoinColumn(name = "apply_candidate_id", nullable = false, referencedColumnName = "id")
    private ApplyCandidate applyCandidate;

    @ManyToOne
    @JoinColumn(name = "file_attachment_id", nullable = false, referencedColumnName = "id")
    private File file;
}
