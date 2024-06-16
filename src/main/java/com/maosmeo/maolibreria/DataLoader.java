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
        //entity.setAuthor("Francesco Costa");
        entity.setScore(5);
        entity.setReviewCount(255);
        entity.setPrice(16.99);

        bookRepository.save(entity);

        BookEntity entity1 = new BookEntity();
        entity1.setName("Il nome della rosa");
        //entity1.setAuthor("Umberto Eco");
        entity1.setScore(4);
        entity1.setReviewCount(1200);
        entity1.setPrice(10.99);
        bookRepository.save(entity1);

        BookEntity entity2 = new BookEntity();
        entity2.setName("1984");
        //entity2.setAuthor("George Orwell");
        entity2.setScore(4);
        entity2.setReviewCount(2500);
        entity2.setPrice(8.99);
        bookRepository.save(entity2);

        BookEntity entity3 = new BookEntity();
        entity3.setName("Il Signore degli Anelli");
        //entity3.setAuthor("J.R.R. Tolkien");
        entity3.setScore(4);
        entity3.setReviewCount(3000);
        entity3.setPrice(20.99);
        bookRepository.save(entity3);
    }
}
