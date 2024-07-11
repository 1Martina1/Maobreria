package com.maosmeo.maolibreria.service;

import com.maosmeo.maolibreria.dto.book.BookDTO;
import com.maosmeo.maolibreria.dto.book.GetBooksResponseDTO;
import com.maosmeo.maolibreria.dto.book.InsertAuthorRequestDTO;
import com.maosmeo.maolibreria.dto.book.InsertBookRequestDTO;
import com.maosmeo.maolibreria.exceptions.ExistingResourceException;
import com.maosmeo.maolibreria.exceptions.ResourceNotFoundException;
import com.maosmeo.maolibreria.repository.AuthorRepository;
import com.maosmeo.maolibreria.repository.BookRepository;
import com.maosmeo.maolibreria.repository.entity.AuthorEntity;
import com.maosmeo.maolibreria.repository.entity.BookEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

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
            bookDTO.setPublicationDate(entity.getPublicationDate());

            books.add(bookDTO);
        }

        getBooksResponseDTO.setBooks(books);


        return getBooksResponseDTO;
    }

    public Boolean insertNewBook(InsertBookRequestDTO insertBookRequestDTO){
        List<BookEntity> existingBooks = bookRepository.findByNameAndAuthor_Id(insertBookRequestDTO.getName(), insertBookRequestDTO.getAuthorId());

        if (existingBooks.isEmpty()) {
            BookEntity bookEntity = new BookEntity();

            bookEntity.setName(insertBookRequestDTO.getName());
            bookEntity.setPrice(insertBookRequestDTO.getPrice());
            bookEntity.setScore(insertBookRequestDTO.getScore());
            bookEntity.setReviewCount(insertBookRequestDTO.getReviewCount());
            bookEntity.setPlot(insertBookRequestDTO.getPlot());
            bookEntity.setPublicationDate(insertBookRequestDTO.getPublicationDate());
            AuthorEntity authorById = authorRepository.findById(insertBookRequestDTO.getAuthorId()).orElseThrow(() -> new ResourceNotFoundException("L'autore non è stato trovato"));

            bookEntity.setAuthor(authorById);

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
            bookDTO.setPublicationDate(bookEntity.getPublicationDate());

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
            throw new ResourceNotFoundException("Il libro non è stato trovato");
        }
    }

    public String insertAuthor (InsertAuthorRequestDTO insertAuthorRequestDTO) {
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
            throw new ExistingResourceException("Autore già presente");
        }
    }

    public GetBooksResponseDTO getLatestReleases(Integer page, Integer size){
        GetBooksResponseDTO getBooksResponseDTO = new GetBooksResponseDTO();

        Pageable pageable = PageRequest.of(page, size);
        Long date = LocalDate.now(ZoneId.of("Europe/Rome")).minusDays(60).atStartOfDay(ZoneId.systemDefault())
                .toInstant().toEpochMilli();

        List<BookDTO> booksDTO = bookRepository.findByPublicationDateGreaterThanEqual(date, pageable).stream().map(b -> {

            BookDTO bookDTO = new BookDTO();
            bookDTO.setName(b.getName());
            bookDTO.setScore(b.getScore());
            bookDTO.setReviewCount(b.getReviewCount());
            bookDTO.setPrice(b.getPrice());
            bookDTO.setPublicationDate(b.getPublicationDate());

            return bookDTO;
        }).toList();

        getBooksResponseDTO.setBooks(booksDTO);
        return getBooksResponseDTO;
}

}
