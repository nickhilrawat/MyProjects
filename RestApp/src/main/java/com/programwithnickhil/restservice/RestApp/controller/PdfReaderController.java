package com.programwithnickhil.restservice.RestApp.controller;

import com.programwithnickhil.restservice.RestApp.dto.PdfDetailsDto;
import com.programwithnickhil.restservice.RestApp.dto.Response;
import com.programwithnickhil.restservice.RestApp.service.PdfUploaderService;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created a controller for pdfReader functions, all the pdf reader related tasks and endpoints will
 * be stored in this controller.
 *
 * @author Nickhil Rawat
 */
@RestController
@RequestMapping("/pdfReader")
public class PdfReaderController {

  /** Injecting pdfUploaderService for the services provided. */
  @Autowired PdfUploaderService pdfUploaderService;

  @Autowired HttpServletRequest request;

  /**
   * This API is used to upload a pdf file to our server and which is then processed by an OCR for
   * further enrichment.
   *
   * @param file file to be uploaded.
   * @return returns the dto of the enriched data form uploaded file.
   */
  @PostMapping("/uploadPdf")
  public Response<PdfDetailsDto> uploadPdf(@RequestParam MultipartFile file) {
    return Response.success(pdfUploaderService.uploadPdf(file));
  }

  /**
   * This API is used to update some data which is already stored in the sql table via invoiceNo.
   *
   * @param pdfDetailsDto dto with details to be updated.
   * @return pdfDetails dto with updated data.
   */
  @PutMapping("/updatePdfDetails")
  public Response<PdfDetailsDto> updatePdfDetails(@RequestBody PdfDetailsDto pdfDetailsDto) {
    String emsg = "error occured while updating pdf details, please contact support. error code: ";
    request.setAttribute("emsg", emsg);
    return Response.success(pdfUploaderService.updatePdfData(pdfDetailsDto));
  }

  /**
   * This API is used to fetch data form the database based on the invoice number.
   *
   * @param invoiceNo invoice number of the invoice.
   * @return PdfDetailsDto having details of that invoice.
   */
  @GetMapping("/getPdfDetails/{invoiceNo}")
  public Response<PdfDetailsDto> getPdfDetails(@PathVariable String invoiceNo) {
    return Response.success(pdfUploaderService.getPdfData(invoiceNo));
  }

  /**
   * This API is used to update digitization status of the invoice data.
   *
   * @param invoiceNo invoice umber to be marked digitized.
   * @return pdfDetails dto with updated data.
   */
  @PutMapping("/markInvoiceDigitized/{invoiceNo}")
  public Response<PdfDetailsDto> updatePdfDetails(@PathVariable String invoiceNo) {
    String emsg = "error occured while updating pdf details, please contact support. error code: ";
    request.setAttribute("emsg", emsg);
    return Response.success(pdfUploaderService.updateStatus(invoiceNo));
  }

  /**
   * This API is used to fetch digitization status of a pdf via invoice number.
   *
   * @param invoiceNo invoice number of the invoice.
   * @return digitization status.
   */
  @GetMapping("/getPdfStatus/{invoiceNo}")
  public Response<String> getPdfStatus(@PathVariable String invoiceNo) {
    return Response.success(pdfUploaderService.getPdfStatus(invoiceNo));
  }
  
}
