package com.lawencon.jobportal.persistence.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@SuppressWarnings("deprecation")
@Entity
@Table(name = "tb_users",
                uniqueConstraints = {
                                @UniqueConstraint(name = "user_username_bk",
                                                columnNames = {"username"}),
                                @UniqueConstraint(name = "user_email_bk", columnNames = {"email"})})
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE tb_users SET deleted_at = now() WHERE id=? AND version =?")
@Where(clause = "deleted_at IS NULL")
public class User extends MasterEntity {
        @ManyToOne
        @JoinColumn(name = "role_id", nullable = false, referencedColumnName = "id")
        private Role role;

        @Column(name = "username", nullable = false)
        private String username;

        @Column(name = "password", nullable = false)
        private String password;

        @Column(name = "email", nullable = false)
        private String email;


}
