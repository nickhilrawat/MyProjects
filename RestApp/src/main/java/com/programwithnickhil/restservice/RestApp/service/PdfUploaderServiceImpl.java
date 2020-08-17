package com.programwithnickhil.restservice.RestApp.service;

import com.programwithnickhil.restservice.RestApp.dto.PdfDetailsDto;
import com.programwithnickhil.restservice.RestApp.enums.DigitizationStatus;
import com.programwithnickhil.restservice.RestApp.exceptions.PdfReaderException;
import com.programwithnickhil.restservice.RestApp.model.PdfDetailsModel;
import com.programwithnickhil.restservice.RestApp.repository.PdfDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
public class PdfUploaderServiceImpl implements PdfUploaderService {

  /**
   * Autowired pdfDetailsRepository dependency for database operations.
   */
  @Autowired
  private PdfDetailsRepository pdfDetailsRepository;

  /**
   * This API is used to upload a pdf file to our server and which is then processed by an OCR for further enrichment.
   * @param file file to be uploaded.
   * @return returns the dto of the enriched data form uploaded file.
   */
  @Override
  public PdfDetailsDto uploadPdf(MultipartFile file) {

    /** 
     * here assuming that the file has been uploaded to s3 bucket and OCR has detected data from it,
     * we'll map all the enriched data to the DTO and save it to our mysqlDB.
     */
    
    PdfDetailsModel pdfDetailsModel = new PdfDetailsModel();
    pdfDetailsModel.setConsigneeAddress("New Delhi");
    pdfDetailsModel.setConsignorAddress("Bombay");
    pdfDetailsModel.setInvoiceDate(System.currentTimeMillis());
    pdfDetailsModel.setInvoiceNo("INV"+ System.currentTimeMillis());
    pdfDetailsModel.setEWaybillExpiryTime(System.currentTimeMillis());
    pdfDetailsModel.setEWaybillNumber(String.valueOf(System.currentTimeMillis()));
    pdfDetailsModel.setInvoiceValue(1200D);
    pdfDetailsModel.setNumberOfBoxes(10);
    pdfDetailsModel.setWeight(12D);
    pdfDetailsModel.setStatus(DigitizationStatus.IN_PROGRESS);

    return convertFromPdfModel(pdfDetailsRepository.save(pdfDetailsModel));
  }

  /**
   * This API is used to fetch data form the database based on the invoice number.
   * @param invoiceNo invoice number of the invoice.
   * @return PdfDetailsDto having details of that invoice.
   */
  @Override
  public PdfDetailsDto getPdfData(String invoiceNo) {
    /** checking if any entry is present in the DB with the given invoiceNo */
    PdfDetailsModel pdfDetailsModel = pdfDetailsRepository.findByInvoiceNo(invoiceNo);
    if(pdfDetailsModel == null){
      throw new PdfReaderException("Invoice with this id does not exist");
    }
    if(DigitizationStatus.IN_PROGRESS.equals(pdfDetailsModel.getStatus())){
      throw new PdfReaderException("Invoice with this id is not yet digitized");
    }
    return convertFromPdfModel(pdfDetailsModel);
  }

  /**
   * This API is used to update some data which is already stored in the sql table via invoiceNo.
   * @param pdfDetailsDto dto with details to be updated.
   * @return pdfDetails dto with updated data.
   */
  @Override
  public PdfDetailsDto updatePdfData(PdfDetailsDto pdfDetailsDto) {
    /** checking if any entry is present in the DB with the given invoiceNo */
    PdfDetailsModel pdfDetailsModel = pdfDetailsRepository.findByInvoiceNo(pdfDetailsDto.getInvoiceNo());
    if(pdfDetailsModel == null){
      throw new PdfReaderException("Invoice with this id does not exist");
    }
    return convertFromPdfModel(pdfDetailsRepository.save(convertToPdfModel(pdfDetailsModel, pdfDetailsDto)));
  }
  
  /**
   * Converter to convert pdfDetailsModel to PdfDetailsDto.
   * @param pdfDetailsModel model returned from DB.
   * @return PdfDetailsDto.
   */
  private PdfDetailsDto convertFromPdfModel(PdfDetailsModel pdfDetailsModel) {
    PdfDetailsDto pdfDetailsDto = PdfDetailsDto.builder().build();
    Optional.ofNullable(pdfDetailsModel.getId()).ifPresent(pdfDetailsDto::setInvoiceId);
    Optional.ofNullable(pdfDetailsModel.getInvoiceValue()).ifPresent(pdfDetailsDto::setInvoiceValue);
    Optional.ofNullable(pdfDetailsModel.getInvoiceNo()).ifPresent(pdfDetailsDto::setInvoiceNo);
    Optional.ofNullable(pdfDetailsModel.getInvoiceDate()).ifPresent(pdfDetailsDto::setInvoiceDate);

    Optional.ofNullable(pdfDetailsModel.getNumberOfBoxes()).ifPresent(pdfDetailsDto::setNumberOfBoxes);
    Optional.ofNullable(pdfDetailsModel.getWeight()).ifPresent(pdfDetailsDto::setWeight);

    Optional.ofNullable(pdfDetailsModel.getEWaybillExpiryTime()).ifPresent(pdfDetailsDto::setEWaybillExpiryTime);
    Optional.ofNullable(pdfDetailsModel.getEWaybillNumber()).ifPresent(pdfDetailsDto::setEWaybillNumber);

    Optional.ofNullable(pdfDetailsModel.getConsignorAddress()).ifPresent(pdfDetailsDto::setConsignorAddress);
    Optional.ofNullable(pdfDetailsModel.getConsigneeAddress()).ifPresent(pdfDetailsDto::setConsigneeAddress);
    Optional.ofNullable(pdfDetailsModel.getStatus()).ifPresent(pdfDetailsDto::setStatus);
    return pdfDetailsDto;
//    return objectMapper.convertValue(pdfDetailsModel, PdfDetailsDto.class);
  }

  /**
   * Converter to convert PdfDetailsDto to pdfDetailsModel.
   * @param pdfDetailsModel model from DB.
   * @param pdfDetailsDto dto with the data in the model.
   * @return model.
   */
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
    Optional.ofNullable(pdfDetailsDto.getStatus()).ifPresent(pdfDetailsModel::setStatus);
    return pdfDetailsModel;
  }

  /**
   * This API is used to update digitization status of the invoice data.
   *
   * @param invoiceNo invoice umber to be marked digitized.
   * @return pdfDetails dto with updated data.
   */
  @Override
  public PdfDetailsDto updateStatus(String invoiceNo) {
    /** checking if any entry is present in the DB with the given invoiceNo */
    PdfDetailsModel pdfDetailsModel = pdfDetailsRepository.findByInvoiceNo(invoiceNo);
    if(pdfDetailsModel == null){
      throw new PdfReaderException("Invoice with this id does not exist");
    }
    pdfDetailsModel.setStatus(DigitizationStatus.DIGITIZED);
    return convertFromPdfModel(pdfDetailsRepository.save(pdfDetailsModel));
  }

  /**
   * This API is used to fetch digitization status of a pdf via invoice number.
   *
   * @param invoiceNo invoice number of the invoice.
   * @return digitization status.
   */
  @Override
  public String getPdfStatus(String invoiceNo){
    PdfDetailsModel pdfDetailsModel = pdfDetailsRepository.findByInvoiceNo(invoiceNo);
    if(pdfDetailsModel == null){
      throw new PdfReaderException("Invoice with this id does not exist");
    }
    return "Pdf digitization status is : " + pdfDetailsModel.getStatus();
  }
}
