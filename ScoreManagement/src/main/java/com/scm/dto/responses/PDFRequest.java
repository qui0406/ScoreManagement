package com.scm.dto.responses;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class PDFRequest {
    private MultipartFile file;
}
