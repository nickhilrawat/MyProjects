CREATE TABLE `pdf_details` (
  `id` bigint NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `invoice_no` varchar(20) NOT NULL UNIQUE,
  `invoice_value` bigint NOT NULL,
  `invoice_date` bigint NOT NULL,
  `number_of_boxes` bigint NOT NULL,
  `weight` bigint NOT NULL,
  `eway_bill_expiry_time` bigint NOT NULL,
  `eway_bill_number` bigint NOT NULL,
  `consignor_address` varchar(20) NOT NULL,
    `consignee_address` varchar(20) NOT NULL,
    `status` varchar(20) NOT NULL
);