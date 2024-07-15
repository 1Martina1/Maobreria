package com.maosmeo.maolibreria.repository.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "BOOKS")
public class RoleEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;
    @Column(name="ROLE_NAME")
    private String roleName;
    @Column(name="IS_TECH_USER")
    private Boolean isTechUser;
}
