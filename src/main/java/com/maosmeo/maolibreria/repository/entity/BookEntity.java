package com.maosmeo.maolibreria.repository.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

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
    @Column(name="SCORE")
    private Integer score;
    @Column(name="LITERARY_GENRE")
    private String literaryGenre;
    @Column(name="REVIEW_COUNT")
    private Integer reviewCount;
    @Column(name="PRICE")
    private Double price;
    @Column(name="PLOT")
    private String plot;
    @Column(name="PUBLICATION_DATE")
    private Long publicationDate;
    @ManyToOne
    @JoinColumn(name="AUTHOR_FK", nullable=true)
    private AuthorEntity author;
    @OneToMany(mappedBy = "book", fetch = FetchType.LAZY)
    private Set<StockEntity> stock;
}
