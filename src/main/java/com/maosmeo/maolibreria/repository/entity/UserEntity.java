package com.maosmeo.maolibreria.repository.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "USERS")
public class UserEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;
    @Column(name="NAME")
    private String name;
    @Column(name="SURNAME")
    private String surname;
    @Column(name="EMAIL")
    private String email;
    @Column(name="BIRTHDATE")
    private String birthDate;
    @Column(name="TELEPHONE")
    private String telephone;
    @Column(name="PASSWORD")
    private String password;
    @ManyToOne
    @JoinColumn(name="ROLE_FK", nullable=true)
    private RoleEntity roleEntity;
}
