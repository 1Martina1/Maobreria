package com.maosmeo.maolibreria.repository;

import com.maosmeo.maolibreria.repository.entity.LibraryEntity;
import com.maosmeo.maolibreria.repository.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LibraryRepository extends JpaRepository<LibraryEntity, Integer> {
    Optional<LibraryEntity> findByStreetNameAndStreetNumber(String streetName, String streetNumber);

}
