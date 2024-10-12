package com.maosmeo.maolibreria.repository.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "LIBRARY")
public class LibraryEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;
    @Column(name="LIBRARY_NAME")
    private String libraryName;
    @Column(name="STREET_NAME")
    private String streetName;
    @Column(name="STREET_NUMBER")
    private String streetNumber;
    @Column(name="WEEKDAYS_OPENING_TIME")
    private Long weekdaysOpeningTime;
    @Column(name="WEEKDAYS_CLOSING_TIME")
    private Long weekdaysClosingTime;
    @Column(name="WEEKEND_OPENING_TIME")
    private Long weekendOpeningTime;
    @Column(name="WEEKEND_CLOSING_TIME")
    private Long weekendClosingTime;
    @OneToMany(mappedBy = "library", fetch = FetchType.LAZY)
    private Set<StockEntity> stock;
}
