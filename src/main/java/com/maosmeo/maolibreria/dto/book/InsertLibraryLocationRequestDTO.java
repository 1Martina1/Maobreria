package com.maosmeo.maolibreria.dto.book;

import lombok.Data;

@Data
public class InsertLibraryLocationRequestDTO {
    private String libraryName;
    private String streetName;
    private String streetNumber;
    private Long weekdaysOpeningTime;
    private Long weekdaysClosingTime;
    private Long weekendOpeningTime;
    private Long weekendClosingTime;
}
