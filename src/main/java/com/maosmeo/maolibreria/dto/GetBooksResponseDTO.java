package com.maosmeo.maolibreria.dto;

import lombok.Data;

import java.util.List;

@Data
public class GetBooksResponseDTO {
    private List<BookDTO> books;

}
