package com.cts.careNexus.billingandinsurance.dto;

import com.cts.careNexus.billingandinsurance.enums.InvoiceStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class InvoiceDTO {

    private Long invoiceID;
    private Long patientId;
    private Long consultationId;
    private double totalAmount;
    private double paidAmount;
    private double outstandingAmount;
    private LocalDateTime invoiceDate;
    private InvoiceStatus status;
}