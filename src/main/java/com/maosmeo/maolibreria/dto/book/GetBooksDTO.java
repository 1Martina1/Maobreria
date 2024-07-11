package com.maosmeo.maolibreria.dto.book;

import lombok.Data;

import java.util.List;

@Data
public class GetBooksDTO {
    private List<BookDTO> books;

}
