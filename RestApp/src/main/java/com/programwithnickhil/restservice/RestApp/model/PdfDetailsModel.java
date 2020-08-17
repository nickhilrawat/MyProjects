package com.programwithnickhil.restservice.RestApp.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.dom4j.tree.AbstractEntity;

@Entity
@Getter
@Setter
@Table(name = "pdf_details")
public class PdfDetailsModel extends AbstractEntity implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "invoice_no")
  private String invoiceNo;

  @Column(name = "invoice_value")
  private Double invoiceValue;

  @Column(name = "invoice_date")
  private Long invoiceDate;

  @Column(name = "number_of_boxes")
  private Integer numberOfBoxes;

  @Column(name = "weight")
  private Double weight;

  @Column(name = "eway_bill_expiry_time")
  private Long eWaybillExpiryTime;

  @Column(name = "eway_bill_number")
  private String eWaybillNumber;

  @Column(name = "consignor_address")
  private String consignorAddress;

  @Column(name = "consignee_address")
  private String consigneeAddress;
}
