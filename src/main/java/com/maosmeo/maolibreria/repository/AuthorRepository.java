package com.maosmeo.maolibreria.repository;

import com.maosmeo.maolibreria.repository.entity.AuthorEntity;
import com.maosmeo.maolibreria.repository.entity.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<AuthorEntity, Integer> {
    List<AuthorEntity> findByNameAndSurname(String name, String surname);
}
