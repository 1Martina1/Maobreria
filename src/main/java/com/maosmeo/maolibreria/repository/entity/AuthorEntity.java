package com.maosmeo.maolibreria.repository.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "AUTHORS")
public class AuthorEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;
    @Column(name="NAME")
    private String name;
    @Column(name="SURNAME")
    private String surname;
    @Column(name="BIRTHDATE")
    private Long birthDate;
    @Column(name="DESCRIPTION")
    private String description;
    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY)
    private Set<BookEntity> books;
}
