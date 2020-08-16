package com.programwithnickhil.restservice.RestApp.controller;

import com.programwithnickhil.restservice.RestApp.dto.PdfDetailsDto;
import com.programwithnickhil.restservice.RestApp.dto.Response;
import com.programwithnickhil.restservice.RestApp.service.PdfUploaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/pdfReader")
public class PdfReaderController {

  @Autowired PdfUploaderService pdfUploaderService;
  @Autowired HttpServletRequest request;

  @GetMapping("/")
  public String index() {
    return "Hello World";
  }

  @PostMapping("/uploadPdf")
  public String uploadPdf(@RequestParam MultipartFile file) {
    pdfUploaderService.uploadPdf(file);
    return "File uploaed";
  }

  @PutMapping("/updatePdfDetails")
  public Response<PdfDetailsDto> updatePdfDetails(@RequestBody PdfDetailsDto pdfDetailsDto) throws Exception {
    String emsg =
            "error occured while updating pdf details, please contact support. error code: ";
    request.setAttribute("emsg", emsg);
    return Response.success(pdfUploaderService.updatePdfData(pdfDetailsDto));
  }

  @GetMapping("/getPdfDetails")
  public Response<PdfDetailsDto> getPdfDetails() {
    return Response.success(pdfUploaderService.getPdfData());
  }
}
