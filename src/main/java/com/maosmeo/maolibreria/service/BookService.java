package com.maosmeo.maolibreria.service;

import com.maosmeo.maolibreria.dto.book.*;
import com.maosmeo.maolibreria.exceptions.ExistingResourceException;
import com.maosmeo.maolibreria.exceptions.ResourceNotFoundException;
import com.maosmeo.maolibreria.repository.AuthorRepository;
import com.maosmeo.maolibreria.repository.BookRepository;
import com.maosmeo.maolibreria.repository.entity.AuthorEntity;
import com.maosmeo.maolibreria.repository.entity.BookEntity;
import jakarta.persistence.Column;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BookService extends BaseService{

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
            bookDTO.setLiteraryGenre(entity.getLiteraryGenre());
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
            bookEntity.setLiteraryGenre(insertBookRequestDTO.getLiteraryGenre());
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
            bookDTO.setLiteraryGenre(bookEntity.getLiteraryGenre());
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
            bookDTO.setLiteraryGenre(b.getLiteraryGenre());
            bookDTO.setScore(b.getScore());
            bookDTO.setReviewCount(b.getReviewCount());
            bookDTO.setPrice(b.getPrice());
            bookDTO.setPublicationDate(b.getPublicationDate());

            return bookDTO;
        }).toList();

        getBooksResponseDTO.setBooks(booksDTO);
        return getBooksResponseDTO;
    }

    public GetInfoAuthorsResponseDTO searchInfoAuthorByLetter(String initialLetter){
        List<AuthorEntity> authorEntity = authorRepository.findByNameStartsWithIgnoreCase(initialLetter);

        List<AuthorDTO> authorDTO = authorEntity
                .stream()
                .sorted(Comparator.comparing(AuthorEntity::getName))
                .map(a -> {
                    AuthorDTO author = new AuthorDTO();
                    author.setFullName(a.getName() + " " + a.getSurname());
                    author.setBirthDate(convertTime(a.getBirthDate()));
                    author.setDescription(a.getDescription());
                    author.setAverageBookPrice(a.getBooks().stream().mapToDouble(BookEntity::getPrice).average().orElse(0.0));

                    LatestBook latestBook = new LatestBook();
                    List<LatestBook> latestBooks =
                            a.getBooks()
                            .stream()
                            .filter(b -> b.getPublicationDate() >= (System.currentTimeMillis() - (94608000000L)))
                        /*
                        b.getPublicationDate (01/01/2020)= 1577833200000
                        System.currentTimeMillis (12/10/2024)= 1728739731000
                        3 anni = 94608000000

                        System.currentTimeMillis - 3anni = 1634131731000


                        1 anno = 365 giorni
                        1 giorno = 24 ore
                        1 ora = 60 minuti
                        1 minuto = 60 secondi
                        1 secondo = 1000 millisecondi

                        3 anni = 1095 giorni
                        1095 giorni = 26280 ore
                        26280 ore = 1576800
                        1576800 minuti = 94608000 secondi
                        94608000 secondi = 94608000000 millisecondi
                        System.currentTimeMilis =

                        01/01/1970 = 0L
                        01/01/1973 = 94608000000L
                         */

                        /*
                        java.util.Date -> new Date()
                        java.date.LocalDate -> LocalDate.now()
                        java.date.Instant -> Instant.now()
                        java.date.Timestamp -> Timestamp.now()
                        Long -> System.currentTimeMillis()


                         */
                            .map(b -> {

                                latestBook.setName(b.getName());
                                latestBook.setPrice(b.getPrice().longValue());
                                latestBook.setPublicationDate(convertTime(b.getPublicationDate()));
                                latestBook.setPlot(b.getPlot());

                                return latestBook;
                            }).collect(Collectors.toList());
                    author.setLatestBooks(latestBooks);

                    return author;

                }).toList();


        return new GetInfoAuthorsResponseDTO(authorDTO);
    }

    public String convertTime(long time){
        Date date = new Date(time);
        Format format = new SimpleDateFormat("yyyy MM dd HH:mm:ss");
        return format.format(date);
    }
}
