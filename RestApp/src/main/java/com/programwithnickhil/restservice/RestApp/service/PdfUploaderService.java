package com.programwithnickhil.restservice.RestApp.service;

import com.programwithnickhil.restservice.RestApp.dto.PdfDetailsDto;
import org.springframework.web.multipart.MultipartFile;

public interface PdfUploaderService {

  /**
   * This API is used to upload a pdf file to our server and which is then processed by an OCR for
   * further enrichment.
   *
   * @param file file to be uploaded.
   * @return returns the dto of the enriched data form uploaded file.
   */
  PdfDetailsDto uploadPdf(MultipartFile file);

  /**
   * This API is used to fetch data form the database based on the invoice number.
   *
   * @param invoiceNo invoice number of the invoice.
   * @return PdfDetailsDto having details of that invoice.
   */
  PdfDetailsDto getPdfData(String invoiceNo);

  /**
   * This API is used to update some data which is already stored in the sql table via invoiceNo.
   *
   * @param pdfDetailsDto dto with details to be updated.
   * @return pdfDetails dto with updated data.
   */
  PdfDetailsDto updatePdfData(PdfDetailsDto pdfDetailsDto);

  /**
   * This API is used to update digitization status of the invoice data.
   *
   * @param invoiceNo invoice umber to be marked digitized.
   * @return pdfDetails dto with updated data.
   */
  PdfDetailsDto updateStatus(String invoiceNo);

  /**
   * This API is used to fetch digitization status of a pdf via invoice number.
   *
   * @param invoiceNo invoice number of the invoice.
   * @return digitization status.
   */
  String getPdfStatus(String invoiceNo);
}
