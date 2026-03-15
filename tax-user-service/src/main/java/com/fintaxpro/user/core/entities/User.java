package com.fintaxpro.user.core.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import com.fintaxpro.user.enums.Role;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;


@Entity
@Table(name = "users", schema = "core")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String lastName;

    @Column(unique = true, nullable = false)
    private String email;

    //Raw password (Not stored)
    @Transient
    private String password;
    //private String passwordHash;

    //Hashes password (stored)
    private String passwordHash;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @Enumerated(EnumType.STRING)
    private Role role = Role.USER;

}
