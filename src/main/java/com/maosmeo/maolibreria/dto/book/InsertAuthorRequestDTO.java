package com.maosmeo.maolibreria.dto.book;

import lombok.Data;

@Data
public class InsertAuthorRequestDTO {
    private String name;
    private String surname;
    private Long birthDate;
    private String description;
}
