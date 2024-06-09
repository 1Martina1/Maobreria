package com.maosmeo.maolibreria.dto;

import lombok.Data;

@Data
public class BookDTO {
    private String name;
    private String author;
    private Integer score;
    private Integer reviewCount;
    private Double price;
}
