package com.maosmeo.maolibreria.dto.user;

import lombok.Data;

@Data
public class InsertUserResponseDTO {
    private String name;
    private String surname;
    private String email;
    private Long birthDate;
    private Integer telephone;
    private String password;
}
