package com.programwithnickhil.restservice.RestApp.service;

import com.programwithnickhil.restservice.RestApp.dto.PdfDetailsDto;
import com.programwithnickhil.restservice.RestApp.exceptions.PdfReaderException;
import com.programwithnickhil.restservice.RestApp.model.PdfDetailsModel;
import com.programwithnickhil.restservice.RestApp.repository.PdfDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
public class PdfUploaderServiceImpl implements PdfUploaderService {

  @Autowired
  PdfDetailsRepository pdfDetailsRepository;
  @Override
  public boolean uploadPdf(MultipartFile file) {
    return false;
  }

  @Override
  public PdfDetailsDto getPdfData() {
    return PdfDetailsDto.builder()
        .consigneeAddress("New Delhi")
        .consignorAddress("Bombay")
        .invoiceDate(System.currentTimeMillis())
        .invoiceId(1L)
        .invoiceNo("1234")
        .eWaybillExpiryTime(System.currentTimeMillis())
        .eWaybillNumber("124356534254")
        .invoiceValue(1200D)
        .numberOfBoxes(10)
        .weight(12D)
        .build();
  }

  @Override
  public PdfDetailsDto updatePdfData(PdfDetailsDto pdfDetailsDto) throws Exception {

    PdfDetailsModel pdfDetailsModel = pdfDetailsRepository.findByInvoiceNo(pdfDetailsDto.getInvoiceNo());
    if(pdfDetailsModel == null){
      throw new PdfReaderException("Invoice with this id does not exist");
    }
    pdfDetailsRepository.save(convertToPdfModel(pdfDetailsModel, pdfDetailsDto));
    return null;
  }

  private PdfDetailsModel convertToPdfModel(PdfDetailsModel pdfDetailsModel, PdfDetailsDto pdfDetailsDto) {
    Optional.ofNullable(pdfDetailsDto.getInvoiceValue()).ifPresent(pdfDetailsModel::setInvoiceValue);
    Optional.ofNullable(pdfDetailsDto.getInvoiceNo()).ifPresent(pdfDetailsModel::setInvoiceNo);
    Optional.ofNullable(pdfDetailsDto.getInvoiceDate()).ifPresent(pdfDetailsModel::setInvoiceDate);
    
    Optional.ofNullable(pdfDetailsDto.getNumberOfBoxes()).ifPresent(pdfDetailsModel::setNumberOfBoxes);
    Optional.ofNullable(pdfDetailsDto.getWeight()).ifPresent(pdfDetailsModel::setWeight);
    
    Optional.ofNullable(pdfDetailsDto.getEWaybillExpiryTime()).ifPresent(pdfDetailsModel::setEWaybillExpiryTime);
    Optional.ofNullable(pdfDetailsDto.getEWaybillNumber()).ifPresent(pdfDetailsModel::setEWaybillNumber);
    
    Optional.ofNullable(pdfDetailsDto.getConsignorAddress()).ifPresent(pdfDetailsModel::setConsignorAddress);
    Optional.ofNullable(pdfDetailsDto.getConsigneeAddress()).ifPresent(pdfDetailsModel::setConsigneeAddress);
    return pdfDetailsModel;
  }
}
