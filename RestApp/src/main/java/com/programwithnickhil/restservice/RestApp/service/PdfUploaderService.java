package com.programwithnickhil.restservice.RestApp.service;

import com.programwithnickhil.restservice.RestApp.dto.PdfDetailsDto;
import org.springframework.web.multipart.MultipartFile;

public interface PdfUploaderService {

  boolean uploadPdf(MultipartFile file);

  PdfDetailsDto getPdfData();

  PdfDetailsDto updatePdfData(PdfDetailsDto pdfDetailsDto) throws Exception;
}
