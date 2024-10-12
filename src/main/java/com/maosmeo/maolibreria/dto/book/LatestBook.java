package com.maosmeo.maolibreria.dto.book;

import lombok.Data;

@Data
public class LatestBook {
     private String name;
     private Long price;
     private String publicationDate;
     private String plot;
}
