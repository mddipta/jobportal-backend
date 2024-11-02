package com.lawencon.jobportal.persistence.entity;

import java.time.LocalDate;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import com.lawencon.jobportal.constant.GenderType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@SuppressWarnings("deprecation")
@Entity
@Table(name = "tb_profile_users",
                uniqueConstraints = {@UniqueConstraint(name = "profile_user_phone_bk",
                                columnNames = {"phone"})})
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE tb_profile_users SET deleted_at = now() WHERE id=? AND version =?")
@Where(clause = "deleted_at IS NULL")
public class UserProfile extends AuditableEntity {
        @OneToOne
        @JoinColumn(name = "user_id", nullable = false)
        private User user;

        @OneToOne
        @JoinColumn(name = "image_file_id", nullable = true, referencedColumnName = "id")
        private File profilePicture;

        @OneToOne
        @JoinColumn(name = "cv_file_id", nullable = true, referencedColumnName = "id")
        private File fileCv;

        @Column(name = "name", nullable = false)
        private String name;

        @Column(name = "phone", nullable = false)
        private String phone;

        @Column(name = "address", nullable = false)
        private String address;

        @Column(name = "city", nullable = false)
        private String city;

        @Enumerated(EnumType.STRING)
        @Column(name = "gender", nullable = false)
        private GenderType gender;

        @Column(name = "born", nullable = false)
        private LocalDate born;
}
