package com.maosmeo.maolibreria.repository;

import com.maosmeo.maolibreria.repository.entity.BookEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, Integer> {
    List<BookEntity> findByName(String name);

    Page<BookEntity> findByPublicationDateGreaterThanEqual(Long publicationDate, Pageable pageable);

    List<BookEntity> findByNameAndAuthor_Id(String name, Integer id);

    Optional<BookEntity> findByNameAndAuthor_Name(String name, String author);
}
