package com.maosmeo.maolibreria.dto;

import lombok.Data;

@Data
public class InsertBookRequestDTO {
    private String name;
    private String author;
    private Integer score;
    private Integer reviewCount;
    private Double price;
    private String plot;
}
