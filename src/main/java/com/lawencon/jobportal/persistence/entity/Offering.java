package com.lawencon.jobportal.persistence.entity;

import java.time.LocalDate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@SuppressWarnings("deprecation")
@Entity
@Table(name = "tb_offering")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE tb_offering SET deleted_at = now() WHERE id=? AND version =?")
@Where(clause = "deleted_at IS NULL")
public class Offering extends AuditableEntity {

    @OneToOne
    @JoinColumn(name = "stage_process_id", nullable = false, referencedColumnName = "id")
    private StageProcess stageProcess;

    @ManyToOne
    @JoinColumn(name = "offering_status_id", nullable = false, referencedColumnName = "id")
    private OfferingStatus offeringStatus;

    @Column(name = "date_boarding", nullable = false)
    private LocalDate dateBoarding;

    @Column(name = "salary", nullable = true)
    private Long salary;
}
