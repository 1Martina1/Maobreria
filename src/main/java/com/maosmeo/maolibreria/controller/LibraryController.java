package com.maosmeo.maolibreria.controller;

import com.maosmeo.maolibreria.dto.book.GetBooksResponseDTO;
import com.maosmeo.maolibreria.dto.book.InsertLibraryLocationRequestDTO;
import com.maosmeo.maolibreria.dto.book.GetLibraryResponseDTO;
import com.maosmeo.maolibreria.exceptions.ForbiddenException;
import com.maosmeo.maolibreria.service.LibraryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/library")
@Tag(name = "Library Controller", description = "Library API endpoints")
public class LibraryController {
//http://localhost:8080/swagger-ui/index.html

    @Autowired
    private LibraryService libraryService;

    @PostMapping("/location")
    @Operation(summary = "libraryLocation Endpoint", description = "New library location insertion on db")
    public Integer isertLocation(
            @Parameter(description = "New library location insertion on db", required = true)
            @RequestBody InsertLibraryLocationRequestDTO insertLibraryLocationRequestDTO,
            @Parameter(description = "Authorization", required = true)
            @RequestHeader("Authorization") String authHeader){

        if(!libraryService.checkToken(authHeader)){
            throw new ForbiddenException("L'utente non ha il permesso di modificare questi dati");
        }

        return libraryService.isertLocation(insertLibraryLocationRequestDTO);
    }

    @GetMapping("/locations")
    @Operation(summary = "libraryLocation Endpoint", description = "Library location retrieval from db")
    public GetLibraryResponseDTO getLocation(){

        return libraryService.getLocation();
    }

    @GetMapping("/bookLocation")
    @Operation(summary = "getBookLocation Endpoint", description = "Retrieve book location from db")
    public GetLibraryResponseDTO getBookLocation(
            @Parameter(description = "Name of the book", required = true)
            @RequestParam(value = "name") String name,
            @Parameter(description = "Author of the book", required = true)
            @RequestParam(value = "author") String author) {

        return libraryService.getBookLocation(name, author);
    }
}
