package com.maosmeo.maolibreria;

import com.maosmeo.maolibreria.repository.BookRepository;
import com.maosmeo.maolibreria.repository.entity.BookEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements ApplicationRunner {

    private BookRepository bookRepository;

    @Autowired
    public DataLoader(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public void run(ApplicationArguments args) {
        BookEntity entity = new BookEntity();
        entity.setName("Frontiera");
        entity.setAuthor("Francesco Costa");
        entity.setScore(5);
        entity.setReviewCount(255);
        entity.setPrice(16.99);


        bookRepository.save(entity);
    }
}
