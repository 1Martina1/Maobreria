package com.maosmeo.maolibreria.service;

import com.maosmeo.maolibreria.dto.BookDTO;
import com.maosmeo.maolibreria.repository.BookRepository;
import com.maosmeo.maolibreria.dto.GetBooksResponseDTO;
import com.maosmeo.maolibreria.repository.entity.BookEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public GetBooksResponseDTO getBooks(Integer page, Integer size) {
        GetBooksResponseDTO getBooksResponseDTO = new GetBooksResponseDTO();
        List<BookDTO> books = new ArrayList<>();

        Pageable pageable = PageRequest.of(page, size);
        Page<BookEntity> bookEntities = bookRepository.findAll(pageable);

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
