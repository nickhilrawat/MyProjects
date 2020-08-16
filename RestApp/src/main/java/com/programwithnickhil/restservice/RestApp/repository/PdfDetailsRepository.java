package com.programwithnickhil.restservice.RestApp.repository;

import com.programwithnickhil.restservice.RestApp.model.PdfDetailsModel;
import org.springframework.data.repository.CrudRepository;

public interface PdfDetailsRepository extends CrudRepository<PdfDetailsModel, Long> {
    
    public PdfDetailsModel findByInvoiceNo(String invoiceId);
}
