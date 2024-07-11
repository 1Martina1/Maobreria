package com.maosmeo.maolibreria.controller;

import com.maosmeo.maolibreria.dto.book.GetBooksResponseDTO;
import com.maosmeo.maolibreria.dto.book.InsertAuthorRequestDTO;
import com.maosmeo.maolibreria.dto.book.InsertBookRequestDTO;
import com.maosmeo.maolibreria.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/books")
@Tag(name = "Book Controller", description = "Book API endpoints")
public class BookController {
//http://localhost:8080/swagger-ui/index.html

    @Autowired
    private BookService bookService;

    @GetMapping
    @Operation(summary = "getBooks Endpoint", description = "Returns a list of all books")
    public GetBooksResponseDTO getBooks(
            @Parameter(description = "Number of pages to recover", required = true)
            @RequestParam(value = "page") Integer page,
            @Parameter(description = "Size of pages to recover", required = true)
            @RequestParam(value = "size") Integer size) {
        return bookService.getBooks(page, size);
    }

    @PostMapping
    @Operation(summary = "insertNewBook Endpoint", description = "Insert new book on db")
    public Boolean insertNewBook(
            @Parameter(description = "Info about new book", required = true)
            @RequestBody InsertBookRequestDTO insertBookRequestDTO) {
        return bookService.insertNewBook(insertBookRequestDTO);
    }

    @GetMapping("/search")
    @Operation(summary = "getBooks Endpoint", description = "Search a book on db")
    public GetBooksResponseDTO getBooks(
            @Parameter(description = "Name of the book we want to find", required = true)
            @RequestParam(value = "name") String name) {
        return bookService.getBooksForName(name);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "deleteBook Endpoint", description = "Delete a book by id")
    public String deleteBook(
            @Parameter(description = "Id of the book we want to delete", required = true)
            @PathVariable(value = "id") Integer id) {
        return bookService.deleteBook(id);
    }

    @PostMapping("/author")
    @Operation(summary = "isertAuthor Endpoint", description = "Author insertion on db")
    public String isertAuthor(
            @Parameter(description = "Info regarding the new author to be included", required = true)
            @RequestBody InsertAuthorRequestDTO insertAuthorRequestDTO){
        return bookService.insertAuthor(insertAuthorRequestDTO);
    }

    @GetMapping("/news")
    @Operation(summary = "news Endpoint", description = "Recovery of latest published books")
    public GetBooksResponseDTO getLatestReleases(
            @Parameter(description = "Number of pages to recover", required = true)
            @RequestParam(value = "page") Integer page,
            @Parameter(description = "Size of pages to recover", required = true)
            @RequestParam(value = "size") Integer size) {
        return bookService.getLatestReleases(page, size);
    }
}
