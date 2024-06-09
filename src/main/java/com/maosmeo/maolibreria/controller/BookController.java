package com.maosmeo.maolibreria.controller;

import com.maosmeo.maolibreria.dto.GetBooksResponseDTO;
import com.maosmeo.maolibreria.dto.InsertBookRequestDTO;
import com.maosmeo.maolibreria.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping
    public GetBooksResponseDTO getBooks(
            @RequestParam(value = "page") Integer page,
            @RequestParam(value = "size") Integer size) {
        return bookService.getBooks(page, size);
    }

    @PostMapping
    @RequestMapping("")
    public Boolean insertNewBook(
            @RequestBody InsertBookRequestDTO insertBookRequestDTO) {
        return bookService.insertNewBook(insertBookRequestDTO);
    }


    /*
    Creare un'api che prende in input nome, autore, punteggio, numero di recensioni e prezzo e salva un libro nel database.
    Prima di saòvarlo controllare a db se esiste già un libro con lo stesso nome e autore, in tal caso restituire un errore.
     */
}
