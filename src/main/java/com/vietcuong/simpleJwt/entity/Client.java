package com.vietcuong.simpleJwt.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "client", uniqueConstraints = {@UniqueConstraint(columnNames =
        "username")})

public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "client_id")
    private Integer id;

    @NotNull(message = "The full name cannot be null")
    @NotEmpty(message = "The full name cannot be empty")
    @Column(name = "full_name")
    @Size(max = 50)
    private String fullName;

    @NotNull(message = "The username cannot be null")
    @NotEmpty(message = "The username cannot be empty")
    @Column(name = "username")
    @Size(max = 50)
    private String username;

    @NotNull(message = "The password cannot be null")
    @NotEmpty(message = "The password cannot be empty")
    @Column(name = "password")
    private String password;

    @NotNull(message = "The email cannot be null")
    @NotEmpty(message = "The email cannot be empty")
    @Email(message =
            "Invalid email format. Expected format is " + "'example" +
                    "@domain.com")
    @Column(name = "email")
    private String email;

    @NotNull(message = "The date of birth cannot be empty")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "date_of_birth")
    private Date dob;

}
