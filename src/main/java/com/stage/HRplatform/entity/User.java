package com.stage.HRplatform.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
public class User {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    private String lastName;
    private String firstName;
    private String email;
    private String position;
    private String phoneNumber;
    private float salary;
    private Date hireDate;
    private String password;
    private String username;
    private String photo;

    @Enumerated(EnumType.STRING)
    private Role role;


}
