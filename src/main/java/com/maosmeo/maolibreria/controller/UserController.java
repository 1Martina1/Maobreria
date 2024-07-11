package com.maosmeo.maolibreria.controller;

import com.maosmeo.maolibreria.dto.user.InsertUserRequestDTO;
import com.maosmeo.maolibreria.dto.user.UserLoginRequestDTO;
import com.maosmeo.maolibreria.dto.user.UserLoginResponseDTO;
import com.maosmeo.maolibreria.service.UserService;
import com.maosmeo.maolibreria.token.JwtTokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@Tag(name = "Users Controller", description = "Users API endpoints")
public class UserController {

//    @Autowired
//    private final AuthenticationManager authenticationManager;
//    @Autowired
//    private final JwtTokenService jwtTokenService;
    @Autowired
    private UserService userService;


//    public UserController(AuthenticationManager authenticationManager, JwtTokenService jwtTokenService, UserService userService) {
////        this.authenticationManager = authenticationManager;
//        this.jwtTokenService = jwtTokenService;
//        this.userService = userService;
//    }

    @PostMapping
    @Operation(summary = "insertNewUser Endpoint", description = "Insertion of the new user")
    public String insertNewUser(
            @Parameter(description = "Info about new user", required = true)
            @RequestBody InsertUserRequestDTO insertUserRequestDTO){
        return userService.insertNewUser(insertUserRequestDTO);
    }

//    @GetMapping
//    @Operation(summary = "userLogin Endpoint", description = "Login of user")
//    public UserLoginResponseDTO userLogin(
//            @Parameter(description = "Personal data to access the account", required = true)
//            @NonNull @RequestBody UserLoginRequestDTO userLoginRequestDTO){
//
//        userService.userLogin(userLoginRequestDTO.getEmail(), userLoginRequestDTO.getPassword());
//
//        // Autenticazione dell'utente
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(userLoginRequestDTO.getEmail(), userLoginRequestDTO.getPassword()));
//
//        // Generazione del token JWT
//        final UserDetails userDetails = userService.userLogin(userLoginRequestDTO.getEmail(), userLoginRequestDTO.getPassword());
//        final String token = jwtTokenService.generateToken(userDetails);
//
//        // Ritorna il token JWT nell'header della risposta
////        return ResponseEntity.ok(new UserLoginResponseDTO(token));
//    }
}
