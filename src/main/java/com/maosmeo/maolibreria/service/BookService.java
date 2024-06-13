package com.maosmeo.maolibreria.service;

import com.maosmeo.maolibreria.dto.BookDTO;
import com.maosmeo.maolibreria.dto.InsertAuthorRequestDTO;
import com.maosmeo.maolibreria.dto.InsertBookRequestDTO;
import com.maosmeo.maolibreria.repository.AuthorRepository;
import com.maosmeo.maolibreria.repository.BookRepository;
import com.maosmeo.maolibreria.dto.GetBooksResponseDTO;
import com.maosmeo.maolibreria.repository.entity.AuthorEntity;
import com.maosmeo.maolibreria.repository.entity.BookEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    public GetBooksResponseDTO getBooks(Integer page, Integer size) {
        GetBooksResponseDTO getBooksResponseDTO = new GetBooksResponseDTO();
        List<BookDTO> books = new ArrayList<>();

        Pageable pageable = PageRequest.of(page, size);
        Page<BookEntity> bookEntities = bookRepository.findAll(pageable);

        for(BookEntity entity : bookEntities){
            BookDTO bookDTO = new BookDTO();
            bookDTO.setName(entity.getName());
            bookDTO.setScore(entity.getScore());
            bookDTO.setReviewCount(entity.getReviewCount());
            bookDTO.setPrice(entity.getPrice());

            books.add(bookDTO);
        }

        getBooksResponseDTO.setBooks(books);


        return getBooksResponseDTO;
    }

    public Boolean insertNewBook(InsertBookRequestDTO insertBookRequestDTO){
        List<BookEntity> existingBooks = bookRepository.findByNameAndAuthor(insertBookRequestDTO.getName(), insertBookRequestDTO.getAuthor());

        if (existingBooks.isEmpty()) {
            BookEntity bookEntity = new BookEntity();

            bookEntity.setName(insertBookRequestDTO.getName());
            bookEntity.setPrice(insertBookRequestDTO.getPrice());
            bookEntity.setScore(insertBookRequestDTO.getScore());
            bookEntity.setReviewCount(insertBookRequestDTO.getReviewCount());
            bookEntity.setPlot(insertBookRequestDTO.getPlot());
            Optional<AuthorEntity> authorById = authorRepository.findById(insertBookRequestDTO.getAuthorId());
            if(authorById.isPresent()){
                bookEntity.setAuthor(authorById.get());
            }else{
                return false;
            }
            bookRepository.save(bookEntity);

            return true;
        }

        return false;
    }

    public GetBooksResponseDTO getBooksForName (String name){
        GetBooksResponseDTO getBooksResponseDTO = new GetBooksResponseDTO();
        List<BookEntity> bookEntities = bookRepository.findByName(name);
        List<BookDTO> bookDTOS = new ArrayList<>();

        for(BookEntity bookEntity : bookEntities){
            BookDTO bookDTO = new BookDTO();
            bookDTO.setName(bookEntity.getName());
            bookDTO.setScore(bookEntity.getScore());
            bookDTO.setReviewCount(bookEntity.getReviewCount());
            bookDTO.setPrice(bookEntity.getPrice());
            bookDTO.setPlot(bookEntity.getPlot());

            bookDTOS.add(bookDTO);

        }
        getBooksResponseDTO.setBooks(bookDTOS);

        return getBooksResponseDTO;

    }

    public String deleteBook (Integer id){

        boolean existsById = bookRepository.existsById(id);

        if(existsById){
            bookRepository.deleteById(id);
            return "L'elemento è stato eliminato";
        }else {
            return "L'elemento non è stato trovato";
        }
    }

    public String isertAuthor (InsertAuthorRequestDTO insertAuthorRequestDTO) {
        List<AuthorEntity> authorEntity = authorRepository.findByNameAndSurname(insertAuthorRequestDTO.getName(), insertAuthorRequestDTO.getSurname());

        if (authorEntity.isEmpty()) {
            AuthorEntity entity = new AuthorEntity();

            entity.setName(insertAuthorRequestDTO.getName());
            entity.setSurname(insertAuthorRequestDTO.getSurname());
            entity.setBirthDate(insertAuthorRequestDTO.getBirthDate());
            entity.setDescription(insertAuthorRequestDTO.getDescription());

            authorRepository.save(entity);
            return "L'inserimento è avvenuto con successo";
        }else{
            return "Autore già presente";
        }
    }
}
