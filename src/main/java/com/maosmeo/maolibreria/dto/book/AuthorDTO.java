package com.maosmeo.maolibreria.dto.book;

import lombok.Data;

import java.util.List;

@Data
public class AuthorDTO {
    private String fullName;
    private String birthDate;
    private String description;
    private Double averageBookPrice;
    private List<LatestBook> latestBooks;
}
