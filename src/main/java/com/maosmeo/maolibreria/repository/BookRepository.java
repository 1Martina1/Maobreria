package com.maosmeo.maolibreria.repository;

import com.maosmeo.maolibreria.repository.entity.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, Integer> {
    List<BookEntity> findByNameAndAuthor(String name, String auth);
    List<BookEntity> findByName(String name);
}
