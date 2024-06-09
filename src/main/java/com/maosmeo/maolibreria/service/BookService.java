package com.maosmeo.maolibreria.service;

import com.maosmeo.maolibreria.dto.BookDTO;
import com.maosmeo.maolibreria.repository.BookRepository;
import com.maosmeo.maolibreria.dto.GetBooksResponseDTO;
import com.maosmeo.maolibreria.repository.entity.BookEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;
    public GetBooksResponseDTO getBooks() {
        GetBooksResponseDTO getBooksResponseDTO = new GetBooksResponseDTO();
        List<BookDTO> books = new ArrayList<>();
        List<BookEntity> bookEntities = bookRepository.findAll();
        for(BookEntity entity : bookEntities){
            BookDTO bookDTO = new BookDTO();
            bookDTO.setName(entity.getName());
            bookDTO.setAuthor(entity.getAuthor());
            bookDTO.setScore(entity.getScore());
            bookDTO.setReviewCount(entity.getReviewCount());
            bookDTO.setPrice(entity.getPrice());

            books.add(bookDTO);
        }

        getBooksResponseDTO.setBooks(books);

        return getBooksResponseDTO;
    }
}
