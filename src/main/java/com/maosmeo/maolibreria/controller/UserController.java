package com.maosmeo.maolibreria.controller;

import com.maosmeo.maolibreria.dto.book.InsertAuthorRequestDTO;
import com.maosmeo.maolibreria.dto.book.InsertLibraryLocationRequestDTO;
import com.maosmeo.maolibreria.dto.user.InsertUserRequestDTO;
import com.maosmeo.maolibreria.dto.user.UserLoginRequestDTO;
import com.maosmeo.maolibreria.dto.user.UserLoginResponseDTO;
import com.maosmeo.maolibreria.exceptions.ForbiddenException;
import com.maosmeo.maolibreria.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@Tag(name = "Users Controller", description = "Users API endpoints")
public class UserController {
//http://localhost:8080/swagger-ui/index.html

    @Autowired
    private UserService userService;


    @PostMapping
    @Operation(summary = "insertNewUser Endpoint", description = "Insertion of the new user")
    public UserLoginResponseDTO insertNewUser(
            @Parameter(description = "Info about new user", required = true)
            @RequestBody InsertUserRequestDTO insertUserRequestDTO){
        return userService.insertNewUser(insertUserRequestDTO);
    }

    @PostMapping("/login")
    @Operation(summary = "userLogin Endpoint", description = "Login of user")
    public UserLoginResponseDTO userLogin(
            @Parameter(description = "Personal data to access the account", required = true)
            @RequestBody UserLoginRequestDTO userLoginRequestDTO){

        return userService.userLogin(userLoginRequestDTO.getEmail(), userLoginRequestDTO.getPassword());
    }
}
