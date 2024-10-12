package com.maosmeo.maolibreria.service;

import com.maosmeo.maolibreria.dto.book.*;
import com.maosmeo.maolibreria.exceptions.ExistingResourceException;
import com.maosmeo.maolibreria.exceptions.ResourceNotFoundException;
import com.maosmeo.maolibreria.repository.BookRepository;
import com.maosmeo.maolibreria.repository.LibraryRepository;
import com.maosmeo.maolibreria.repository.entity.BookEntity;
import com.maosmeo.maolibreria.repository.entity.LibraryEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LibraryService  extends BaseService{

    @Autowired
    private LibraryRepository libraryRepository;

    @Autowired
    private BookRepository bookRepository;


    public Integer isertLocation(InsertLibraryLocationRequestDTO request) {
        Optional<LibraryEntity> libraryEntities = libraryRepository.findByStreetNameAndStreetNumber(request.getStreetName(), request.getStreetNumber());

        if(libraryEntities.isEmpty()) {
            LibraryEntity libraryEntity = new LibraryEntity();
            libraryEntity.setLibraryName(request.getLibraryName());
            libraryEntity.setStreetName(request.getStreetName());
            libraryEntity.setStreetNumber(request.getStreetNumber());
            libraryEntity.setWeekdaysOpeningTime(request.getWeekdaysOpeningTime());
            libraryEntity.setWeekdaysClosingTime(request.getWeekdaysClosingTime());
            libraryEntity.setWeekendOpeningTime(request.getWeekendOpeningTime());
            libraryEntity.setWeekendClosingTime(request.getWeekendClosingTime());

            libraryEntity = libraryRepository.save(libraryEntity);

            return libraryEntity.getId();
        }
        throw new ExistingResourceException("La sede della libreria è già presente");
    }

    public GetLibraryResponseDTO getLocation() {

        List<LibraryDTO> librariesDTO = libraryRepository.findAll().stream().map(l -> {
            LibraryDTO library = new LibraryDTO();
            library.setLibraryName(l.getLibraryName());
            library.setStreetName(l.getStreetName());
            library.setStreetNumber(l.getStreetNumber());
            library.setWeekdaysOpeningTime(l.getWeekdaysOpeningTime());
            library.setWeekdaysClosingTime(l.getWeekdaysOpeningTime());
            library.setWeekendOpeningTime(l.getWeekendOpeningTime());
            library.setWeekendClosingTime(l.getWeekendClosingTime());

            return library;
        }).toList();

        GetLibraryResponseDTO getLibraryResponseDTO = new GetLibraryResponseDTO();
        getLibraryResponseDTO.setLibraries(librariesDTO);

        return getLibraryResponseDTO;

    }

    public GetLibraryResponseDTO getBookLocation(String name, String author) {

        BookEntity book = bookRepository.findByNameAndAuthor_Name(name, author).orElseThrow(() -> new ResourceNotFoundException("Libro non trovato"));
        List<LibraryDTO> libraries = book.getStock().stream().map(s -> {
            LibraryDTO libraryDTO = new LibraryDTO();
            libraryDTO.setLibraryName(s.getLibrary().getLibraryName());
            libraryDTO.setStreetName(s.getLibrary().getStreetName());
            libraryDTO.setStreetNumber(s.getLibrary().getStreetNumber());
            libraryDTO.setWeekdaysOpeningTime(s.getLibrary().getWeekdaysOpeningTime());
            libraryDTO.setWeekdaysClosingTime(s.getLibrary().getWeekdaysClosingTime());
            libraryDTO.setWeekendOpeningTime(s.getLibrary().getWeekendOpeningTime());
            libraryDTO.setWeekendClosingTime(s.getLibrary().getWeekendClosingTime());
            libraryDTO.setStockCount(s.getStockCount());

            return libraryDTO;
        }).toList();

        GetLibraryResponseDTO response = new GetLibraryResponseDTO();
        response.setLibraries(libraries);
        return response;
    }

}
