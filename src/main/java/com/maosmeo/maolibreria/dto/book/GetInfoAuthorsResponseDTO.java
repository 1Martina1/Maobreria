package com.maosmeo.maolibreria.dto.book;

import lombok.Data;

import java.util.List;


@Data
public class GetInfoAuthorsResponseDTO {
    private List<AuthorDTO> authors;

    public GetInfoAuthorsResponseDTO(List<AuthorDTO> authors) {
        this.authors = authors;
    }
}
