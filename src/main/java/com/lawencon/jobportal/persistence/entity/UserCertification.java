package com.lawencon.jobportal.persistence.entity;

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
@Table(name = "tb_user_certifications")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE tb_user_certifications SET deleted_at = now() WHERE id=? AND version =?")
@Where(clause = "deleted_at IS NULL")
public class UserCertification extends DeletableEntity {
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, referencedColumnName = "id")
    private User user;

    @OneToOne
    @JoinColumn(name = "certif_file_id", nullable = false, referencedColumnName = "id")
    private File certifFile;

    @Column(name = "name", nullable = false)
    private String name;
}
