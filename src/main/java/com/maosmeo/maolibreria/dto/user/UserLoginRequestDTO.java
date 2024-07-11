package com.maosmeo.maolibreria.dto.user;

import lombok.Data;

@Data
public class UserLoginRequestDTO {
    private String email;
    private String password;
}
