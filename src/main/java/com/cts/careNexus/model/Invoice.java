package com.cts.careNexus.model;

import com.cts.careNexus.enums.InvoiceStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "Invoice")
@Data
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Invoice_id")
    private Long invoiceID;

    @Column(name = "Patient_id", nullable = false)
    private Long patientID;

    @Column(name = "Consultation_id")
    private Long consultationID;

    @Column(name = "Total_Amount", nullable = false)
    private double totalAmount;

    @Column(name = "Paid_Amount", nullable = false)
    private double paidAmount = 0.0;

    @Column(name = "Outstanding_Amount", nullable = false)
    private double outstandingAmount;

    @Column(name = "Invoice_Date", nullable = false)
    private LocalDateTime invoiceDate = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    @Column(name = "Status", nullable = false, length = 30)
    private InvoiceStatus status;


    @PrePersist
    @PreUpdate
    public void validate() {
        if (totalAmount < 0 || outstandingAmount < 0) {
            throw new RuntimeException("Amounts cannot be negative");
        }
    }
}