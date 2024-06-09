package com.maosmeo.maolibreria.repository.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "BOOKS")
public class BookEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;
    @Column(name="NAME")
    private String name;
    @Column(name="AUTHOR")
    private String author;
    @Column(name="SCORE")
    private Integer score;
    @Column(name="REVIEW_COUNT")
    private Integer reviewCount;
    @Column(name="PRICE")
    private Double price;
    @Column(name="PLOT")
    private String plot;
}
