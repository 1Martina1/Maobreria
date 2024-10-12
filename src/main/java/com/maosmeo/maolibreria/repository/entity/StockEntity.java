package com.maosmeo.maolibreria.repository.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "STOCK")
public class StockEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    @Column(name="STOCK_COUNT")
    private Integer stockCount;

    @ManyToOne
    @JoinColumn(name="LIBRARY_FK", nullable=false)
    private LibraryEntity library;

    @ManyToOne
    @JoinColumn(name="BOOK_FK", nullable=false)
    private BookEntity book;
}
