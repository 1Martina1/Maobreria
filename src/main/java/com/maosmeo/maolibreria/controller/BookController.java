package com.maosmeo.maolibreria.controller;

import com.maosmeo.maolibreria.dto.GetBooksResponseDTO;
import com.maosmeo.maolibreria.dto.InsertAuthorRequestDTO;
import com.maosmeo.maolibreria.dto.InsertBookRequestDTO;
import com.maosmeo.maolibreria.service.BookService;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Boolean insertNewBook(
            @RequestBody InsertBookRequestDTO insertBookRequestDTO) {
        return bookService.insertNewBook(insertBookRequestDTO);
    }

    @GetMapping("/search")
    public GetBooksResponseDTO getBooks(
            @RequestParam(value = "name") String name) {
        return bookService.getBooksForName(name);
    }

    @DeleteMapping("/{id}")
    public String deleteBook(
            @PathParam(value = "id") Integer id) {
        return bookService.deleteBook(id);
    }

    @PostMapping("/author")
    public String isertAuthor(
            @RequestBody InsertAuthorRequestDTO insertAuthorRequestDTO){
        return bookService.isertAuthor(insertAuthorRequestDTO);
    }

}
