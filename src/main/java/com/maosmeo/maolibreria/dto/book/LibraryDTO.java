package com.maosmeo.maolibreria.dto.book;

import lombok.Data;

@Data
public class LibraryDTO {
    private String libraryName;
    private String streetName;
    private String streetNumber;
    private Long weekdaysOpeningTime;
    private Long weekdaysClosingTime;
    private Long weekendOpeningTime;
    private Long weekendClosingTime;
    private Integer stockCount;
}
