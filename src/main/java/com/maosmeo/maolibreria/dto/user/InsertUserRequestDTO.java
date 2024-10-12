package com.maosmeo.maolibreria.dto.user;

import lombok.Data;

@Data
public class InsertUserRequestDTO {
    private String name;
    private String surname;
    private String email;
    private String birthDate;
    private String telephone;
    private String password;
}
