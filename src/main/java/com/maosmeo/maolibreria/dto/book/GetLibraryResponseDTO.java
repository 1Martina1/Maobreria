package com.maosmeo.maolibreria.dto.book;

import lombok.Data;
import org.apache.tomcat.jni.Library;

import java.util.List;

@Data
public class GetLibraryResponseDTO {
    private List<LibraryDTO> libraries;
}
