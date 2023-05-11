package ru.job4j.auth.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Data
@EqualsAndHashCode
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;
    @NotBlank(message = "Login must be non empty")
    @Email(regexp = (".+[@].+[\\.].+"))
    @Column(name = "login")
    private String login;
    @NotBlank(message = "Password must be non empty")
    @Size(min = 6, message = "Length of password must be 6 and more")
    @Column(name = "password")
    private String password;
}
