package com.maosmeo.maolibreria.dto;

import lombok.Data;

@Data
public class InsertAuthorRequestDTO {
    private String name;
    private String surname;
    private Long birthDate;
    private String description;
}
