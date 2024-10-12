package com.maosmeo.maolibreria.dto.book;

import lombok.Data;

@Data
public class BookDTO {
    private String name;
    private String author;
    private String literaryGenre;
    private Integer score;
    private Integer reviewCount;
    private Double price;
    private String plot;
    private Long publicationDate;
}
