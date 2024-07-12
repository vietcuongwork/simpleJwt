package com.vietcuong.simpleJwtAndValidation.entity;

import com.vietcuong.simpleJwtAndValidation.validator.ValidDateOfBirth;
import com.vietcuong.simpleJwtAndValidation.validator.ValidEmail;
import com.vietcuong.simpleJwtAndValidation.validator.ValidFullName;
import com.vietcuong.simpleJwtAndValidation.validator.ValidUsername;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "client",
        uniqueConstraints = {@UniqueConstraint(columnNames = "username"), @UniqueConstraint(columnNames = "email")})

public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "client_id")
    private Integer id;

    @ValidFullName
    @Column(name = "full_name")
    private String fullName;

    @ValidUsername
    @Column(name = "username")
    private String username;

    @ValidEmail
    @Column(name = "email")
    private String email;

    @ValidDateOfBirth
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

}
