package com.programwithnickhil.restservice.RestApp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PdfDetailsDto {
  private Long invoiceId;
  private String invoiceNo;
  private Double invoiceValue;
  private Long invoiceDate;
  private Integer numberOfBoxes;
  private Double weight;
  private Long eWaybillExpiryTime;
  // This annotation is reqd because of this:
  // https://stackoverflow.com/questions/30205006/why-does-jackson-2-not-recognize-the-first-capital-letter-if-the-leading-camel-c
  @JsonProperty("eWaybillNumber")
  private String eWaybillNumber;

  private String consignorAddress;
  private String consigneeAddress;
}
